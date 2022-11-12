package com.wsu.ltgb.controller;

import com.wsu.ltgb.service.CommentService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/board")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("comment/{board_id}")
    public ResponseEntity<?> Comment(@RequestHeader String auth, @RequestBody String comment, @PathVariable Long board_id){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = commentService.Comment(comment, member, board_id);
        if (!err.IsEmpty()) {
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
