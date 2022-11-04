package com.wsu.ltgb.controller;

import com.wsu.ltgb.dto.LoginDto;
import com.wsu.ltgb.dto.RegisterDto;
import com.wsu.ltgb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        var result = service.Login(loginDto);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("register")
    public  ResponseEntity<?> Register(@RequestBody RegisterDto registerDto) {
        var err = service.Register(registerDto);
        if (err != null){
            return ResponseEntity.status(err.StatusCode).body(err.Message);
        }
        return ResponseEntity.ok().body(true);
    }
}
