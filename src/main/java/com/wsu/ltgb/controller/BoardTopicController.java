package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.BoardTopicDto;
import com.wsu.ltgb.service.BoardTopicService;
import com.wsu.ltgb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
public class BoardTopicController {
    @Autowired
    private BoardTopicService service;
    @Autowired
    private JwtService jwtService;

    @PostMapping("create")
    public ResponseEntity<?> CreateTopic(@RequestHeader String auth, @RequestBody BoardTopicDto req) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = service.CreateTopic(member, req);
        if (!err.IsEmpty()){
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> UpdateTopic(@RequestHeader String auth, @RequestBody BoardTopicDto req, @PathVariable long id) {
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        var member = jwtResult.getSecond();
        err = service.UpdateTopic(member, req, id);
        if (!err.IsEmpty()){
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("remove/{id}")
    public ResponseEntity<?> removeTopic(@RequestHeader String auth, @PathVariable long id){
        var jwtResult = jwtService.ValidateToken(auth);
        var err = jwtResult.getFirst();
        if (!err.IsEmpty()) {
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        err = service.RemoveTopic(id);
        if (!err.IsEmpty()){
            return ResponseEntity.status(err.getStatusCode()).body(err.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("get")
    public ResponseEntity<?> GetTopics(@RequestParam("index") int pageIndex,
                                       @RequestParam("limit") int limit){
        var resp = service.GetList(limit, pageIndex);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("checkTitle/{title}")
    public ResponseEntity<?> CheckTitle(@PathVariable String title){
        return ResponseEntity.ok(service.CheckTitle(title));
    }
}
