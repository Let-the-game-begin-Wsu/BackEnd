package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.BoardRequestDto;
import com.wsu.ltgb.service.BoardService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("write")
    public ResponseEntity<?> CreateBoard(@RequestHeader String auth, @RequestBody BoardRequestDto board) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = boardService.CreateBoard(member, board);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetBoard(@RequestHeader String auth, @PathVariable("id") long id) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var response = boardService.GetBoard(id);
        if (!response.getFirst().IsEmpty()){
            return ResponseEntity.status(response.getFirst().getStatusCode()).body(response.getFirst().getMessage());
        }
        return ResponseEntity.ok(response.getSecond());
    }

    @GetMapping("list")
    public ResponseEntity<?> GetBoardList(@RequestHeader String auth, @RequestParam("index") int pageindex,
                                          @RequestParam("limit") int limit){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var res = boardService.GetBoardList(limit, pageindex);
        if (!res.getFirst().IsEmpty()){
            return ResponseEntity.status(res.getFirst().getStatusCode()).body(res.getFirst().getMessage());
        }
        return ResponseEntity.ok(res.getSecond());
    }

    @PostMapping("upadte/{id}")
    public ResponseEntity<?> UpdateBoard(@RequestHeader String auth, @PathVariable("id") long id,
                                         @RequestBody BoardRequestDto requestDto) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        err = boardService.UpdateBoard(jwtResult.getSecond(), id, requestDto);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> DeleteBoard(@RequestHeader String auth, @PathVariable("id") long id) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        err = boardService.RemoveBoard(jwtResult.getSecond(), id);
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }
}