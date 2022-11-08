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
        if (!boardService.CreateBoard(member, board).IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }
}