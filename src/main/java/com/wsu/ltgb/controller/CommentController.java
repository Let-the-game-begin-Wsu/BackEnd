package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.CommentDto;
import com.wsu.ltgb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/board")
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping("comment")
    public ResponseEntity<?> Comment(@RequestBody CommentDto commentDto){
        var err = service.Comment(commentDto);
        if (err != null){
            return ResponseEntity.status(err.StatusCode).body(err.Message);
        }
        return ResponseEntity.ok().body(true);
    }
}
