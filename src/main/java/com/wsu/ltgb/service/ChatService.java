package com.wsu.ltgb.service;

import com.google.gson.Gson;
import com.wsu.ltgb.dto.*;
import com.wsu.ltgb.model.ChatMemberEntity;
import com.wsu.ltgb.model.ChatMessageEntity;
import com.wsu.ltgb.model.ChatRoomEntity;
import com.wsu.ltgb.model.UserEntity;
import com.wsu.ltgb.persistence.ChatMemberRepository;
import com.wsu.ltgb.persistence.ChatMessageRepository;
import com.wsu.ltgb.persistence.ChatRoomRepository;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@Service
public class ChatService {
    @Autowired
    private ChatMemberRepository chatMemberRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository memberRepository;
    @Autowired
    private JwtService jwtService;

    private final HashMap<Long, HashSet<Long>> RoomEnteredUsers = new HashMap<>();
    private final HashMap<Long, ChatMemberDto> Users = new HashMap<>();

    private Pair<ErrorDto, MemberDto> getMemberDto(WebSocketSession session){
        var auth = session.getHandshakeHeaders().getFirst("auth");
        if (auth == null){
            var err = ErrorDto.builder().StatusCode(401).Message("no auth").build();
            return Pair.of(err, MemberDto.Empty());
        }
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return Pair.of(err, MemberDto.Empty());
        }
        var memberDto = jwtResult.getSecond();
        return Pair.of(ErrorDto.Empty(), memberDto);
    }

    private ErrorDto sendMessage(ChatMessageDto msg) {
        if (!chatRoomRepository.existsById(msg.getRoomId())){
            return ErrorDto.builder().StatusCode(404).Message("not found room").build();
        }
        Gson gson = new Gson();
        var msgEntity = ChatMessageEntity.builder()
                .chatRoom(chatRoomRepository.getReferenceById(msg.getRoomId()))
                .uptime(msg.getUptime())
                .content(gson.toJson(msg))
                .typeCode(msg.getMessageTypeCode());
        if (msg.getMember() != null){
            var chatMem = chatMemberRepository.isEntered(msg.getRoomId(), msg.getMember().getUser_id());
            if (chatMem == null){
                return ErrorDto.builder().StatusCode(403).Message("you are not room member").build();
            }
            msgEntity.user(chatMem.getUser());
        }
        chatMessageRepository.saveAndFlush(msgEntity.build());
        var users = RoomEnteredUsers.get(msg.getRoomId()).stream().map(Users::get).toList();
        users.forEach(x -> {
            try {
                if (x != null){
                    x.getSession().sendMessage(new TextMessage(gson.toJson(msg)));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return ErrorDto.Empty();
    }

    public ErrorDto enterRoom(MemberDto memberDto, Long roomId){
        if (!chatRoomRepository.existsById(roomId)){
            return ErrorDto.builder().StatusCode(404).Message("not found room").build();
        }
        var chatMemberEntity = chatMemberRepository.isEntered(roomId, memberDto.getUser_id());
        if (chatMemberEntity != null){
            return ErrorDto.builder().StatusCode(400).Message("already entered").build();
        }
        var member = memberRepository.getReferenceById(memberDto.getUser_id());
        var room = chatRoomRepository.getReferenceById(roomId);
        if (!chatRoomRepository.existsById(roomId) || !memberRepository.existsById(memberDto.getUser_id())){
            return ErrorDto.builder().StatusCode(404).Message("not found room or user").build();
        }
        var uptime = new Date().getTime();
        chatMemberEntity = ChatMemberEntity.builder()
                .chatRoom(room)
                .user(member)
                .uptime(uptime)
                .build();
        chatMemberRepository.saveAndFlush(chatMemberEntity);
        if (!RoomEnteredUsers.containsKey(roomId)){
            RoomEnteredUsers.put(roomId, new HashSet<>());
        }
        RoomEnteredUsers.get(roomId).add(memberDto.getUser_id());
        var enterMsg = ChatMessageDto.builder()
                .messageType(ChatMessageType.MEMBER_ENTER)
                .messageTypeCode(ChatMessageType.MEMBER_ENTER.getCode())
                .roomId(roomId)
                .uptime(uptime)
                .Message(memberDto)
                .build();
        var err = sendMessage(enterMsg);
        if (!err.IsEmpty()){
            return err;
        }
        return ErrorDto.Empty();
    }

    public ErrorDto leaveRoom(MemberDto memberDto, Long roomId){
        if (!chatRoomRepository.existsById(roomId)){
            return ErrorDto.builder().StatusCode(404).Message("not found room").build();
        }
        var chatMemberEntity = chatMemberRepository.isEntered(roomId, memberDto.getUser_id());
        if (chatMemberEntity == null){
            return ErrorDto.builder().StatusCode(404).Message("not found user").build();
        }
        chatMemberRepository.delete(chatMemberEntity);
        Users.remove(memberDto.getUser_id());
        RoomEnteredUsers.get(roomId).remove(memberDto.getUser_id());
        var leaveMsg = ChatMessageDto.builder()
                .messageType(ChatMessageType.MEMBER_LEAVE)
                .messageTypeCode(ChatMessageType.MEMBER_LEAVE.getCode())
                .roomId(roomId)
                .uptime(new Date().getTime())
                .Message(memberDto)
                .build();
        var err = sendMessage(leaveMsg);
        if (!err.IsEmpty()){
            return err;
        }
        return ErrorDto.Empty();
    }

    public ErrorDto sendMessageToRoom(MemberDto memberDto, Long roomId, String message){
        var uptime = new Date().getTime();
        var msg = ChatMessageDto.builder()
                .messageType(ChatMessageType.CHAT)
                .messageTypeCode(ChatMessageType.CHAT.getCode())
                .member(memberDto)
                .Message(message)
                .roomId(roomId)
                .uptime(uptime)
                .build();
        var err = sendMessage(msg);
        if (!err.IsEmpty()){
            return err;
        }
        return ErrorDto.Empty();
    }

    public void onConnected(WebSocketSession session) throws IOException {
        try {
            Gson gson = new Gson();
            var authResult = getMemberDto(session);
            var err = authResult.getFirst();
            if (!err.IsEmpty()) {
                var msg = ChatMessageDto.builder()
                        .messageType(ChatMessageType.ERR_AUTH)
                        .messageTypeCode(ChatMessageType.ERR_AUTH.getCode())
                        .uptime(new Date().getTime())
                        .Message(err)
                        .build();
                session.sendMessage(new TextMessage(gson.toJson(msg)));
                session.close();
                return;
            }
            var memberDto = authResult.getSecond();
            var enteredRooms = chatMemberRepository.findAll().stream().filter(
                    x -> Objects.equals(x.getUser().getUserId(), memberDto.getUser_id())
            ).map(x-> x.getChatRoom().getChatRoomId()).toList();
            var chatMem = ChatMemberDto.builder()
                    .memberDto(memberDto)
                    .session(session)
                    .enteredRoomIds(enteredRooms)
                    .build();
            Users.put(memberDto.getUser_id(), chatMem);
            for (var roomId : chatMem.getEnteredRoomIds()) {
                if (!RoomEnteredUsers.containsKey(roomId)){
                    RoomEnteredUsers.put(roomId, new HashSet<>());
                }
                RoomEnteredUsers.get(roomId).add(memberDto.getUser_id());
            }
            var msg = ChatMessageDto.builder()
                    .messageType(ChatMessageType.CONNECT_SUCCESS)
                    .messageTypeCode(ChatMessageType.CONNECT_SUCCESS.getCode())
                    .uptime(new Date().getTime())
                    .Message("Connected")
                    .build();
            session.sendMessage(new TextMessage(gson.toJson(msg)));
        }catch(Exception exception) {
            Gson gson = new Gson();
            var msg = ChatMessageDto.builder()
                    .messageType(ChatMessageType.ERR_AUTH)
                    .messageTypeCode(ChatMessageType.ERR_AUTH.getCode())
                    .uptime(new Date().getTime())
                    .Message("fail to connect")
                    .build();
            session.sendMessage(new TextMessage(gson.toJson(msg)));
            session.close();
        }
    }

    public void onClosedConnection(WebSocketSession session){
        var authResult = getMemberDto(session);
        var err = authResult.getFirst();
        if (!err.IsEmpty()) {
            return;
        }
        var memberDto = authResult.getSecond();
        Users.remove(memberDto.getUser_id());
        RoomEnteredUsers.values().forEach(x -> x.remove(memberDto.getUser_id()));
    }
}
