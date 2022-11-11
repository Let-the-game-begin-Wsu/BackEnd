package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.CommentDto;
import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.persistence.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public ErrorDto Comment(CommentDto dto){
        if (dto == null){
            return ErrorDto.builder().StatusCode(400).Message("dto is null").build();
        }
        var content=repository.board_comment_id(dto.board_comment_id);
        if (content == null){
            return ErrorDto.builder().StatusCode(404).Message("").build();
        }
        return null;
    }
}
