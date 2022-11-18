package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.CommentRequestDto;
import com.wsu.ltgb.service.CommentService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("write/{board_id}")
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

    @GetMapping("list/{boardId}")
    public ResponseEntity<?> GetCommentList(@RequestHeader String auth,  @RequestParam("index") int pageindex,
                                            @RequestParam("limit") int limit,@PathVariable("boardId")Long boardId) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var res=commentService.GetCommentList(boardId,limit,pageindex);
        if(!res.getFirst().IsEmpty()){
            return ResponseEntity.status(res.getFirst().getStatusCode()).body(res.getFirst().getMessage());
        }
        return  ResponseEntity.ok(res.getSecond());
    }
    @PostMapping("update")
    public ResponseEntity<?> UpdateComment(@RequestHeader String auth,
                                           @RequestBody CommentRequestDto requestDto) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        err = commentService.UpdateComment(jwtResult.getSecond(), requestDto);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

}
