package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.*;
import com.wsu.ltgb.model.BoardEntity;
import com.wsu.ltgb.persistence.BoardRepository;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BoardService {
    @Autowired
    private BoardRepository repository;
    @Autowired
    private UserRepository userRepository;

    public ErrorDto CreateBoard(MemberDto memberDto, BoardRequestDto requestDto) {
        if (memberDto.IsEmpty()){
            return ErrorDto.builder().StatusCode(403).Message("bad token").build();
        }
        if (requestDto == null){
            return ErrorDto.builder().StatusCode(400).Message("bad req").build();
        }
        var memberEntity = userRepository.findById(memberDto.getUser_id());
        if (memberEntity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("member not found").build();
        }

        var entity = BoardEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getComment())
                .user(memberEntity.get())
                .uptime(new Date().getTime())
                .build();

        repository.saveAndFlush(entity);
        return ErrorDto.Empty();
    }

    public Pair<ErrorDto, BoardDetailDto> GetBoard(long boardId){
        if (!repository.existsById(boardId)){
            var err = ErrorDto.builder().StatusCode(404).Message("board not found").build();
            return Pair.of(err, BoardDetailDto.builder().build());
        }
        var entity = repository.getReferenceById(boardId);
        var userEntity = entity.getUser();
        var user = BoardDetailUserDto.builder()
                .id(userEntity.getUser_id())
                .nickname(userEntity.getNickname())
                .image(userEntity.getImage())
                .build();
        var content = BoardDetailContentDto.builder()
                .content(entity.getContent())
                .uptime(entity.getUptime())
                .title(entity.getTitle())
                .id(entity.getBoard_id())
                .build();
        var response = BoardDetailDto.builder()
                .content(content)
                .user(user)
                .build();
        return Pair.of(ErrorDto.Empty(), response);
    }

    public  Pair<ErrorDto, BoardListDto> GetBoardList(int listLimit, int pageIndex) {
        var offset = 0;
        if (pageIndex > 1){
            offset = (pageIndex -1) * listLimit;
        }
        var list =repository.GetBoardList(listLimit, offset).stream().map(
                x -> BoardListItemDto.builder()
                        .userId(x.getUser().getUser_id())
                        .userNick(x.getUser().getNickname())
                        .uptime(x.getUptime())
                        .id(x.getBoard_id())
                        .title(x.getTitle())
                        .build()
        ).toList();
        var count = repository.GetBoardCount();
        var response = BoardListDto.builder().boardCount(count).items(list).pageIndex(pageIndex).build();
        return Pair.of(ErrorDto.Empty(), response);
    }
}
