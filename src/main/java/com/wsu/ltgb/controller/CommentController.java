package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.BoardRequestDto;
import com.wsu.ltgb.dto.CommentDto;
import com.wsu.ltgb.service.CommentService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("write/{comment}")
    public ResponseEntity<?> Comment(@RequestHeader String auth, @RequestBody CommentDto commentDto){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        if (!commentService.Comment(commentDto, member).IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> GetComment(@RequestHeader String auth, @PathVariable("id") long id) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var response = commentService.GetComment(id);
        if (!response.getFirst().IsEmpty()){
            return ResponseEntity.status(response.getFirst().getStatusCode()).body(response.getFirst().getMessage());
        }
        return ResponseEntity.ok(response.getSecond());
    }
}
