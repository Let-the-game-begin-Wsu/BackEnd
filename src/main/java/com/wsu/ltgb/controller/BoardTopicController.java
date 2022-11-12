package com.wsu.ltgb.controller;

import com.wsu.ltgb.service.BoardTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
public class BoardTopicController {
    @Autowired
    private BoardTopicService service;
}
