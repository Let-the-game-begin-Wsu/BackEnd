package com.wsu.ltgb.controller;

import com.wsu.ltgb.service.ChatService;
import com.wsu.ltgb.service.CommentService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("enter/{room_id}")
    public ResponseEntity<?> enterRoom(@RequestHeader String auth, @PathVariable Long room_id){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = chatService.enterRoom(member, room_id);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("leave/{room_id}")
    public ResponseEntity<?> leaveRoom(@RequestHeader String auth, @PathVariable Long room_id){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = chatService.leaveRoom(member, room_id);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("send/{room_id}")
    public ResponseEntity<?> sendMessage(@RequestHeader String auth, @PathVariable Long room_id, @RequestBody String message){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = chatService.sendMessageToRoom(member, room_id, message);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }
}
