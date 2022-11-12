package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.*;
import com.wsu.ltgb.model.BoardCommentEntity;
import com.wsu.ltgb.persistence.BoardCommentRepository;
import com.wsu.ltgb.persistence.BoardRepository;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentService {

    @Autowired
    private BoardCommentRepository repository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    public ErrorDto Comment(String comment, MemberDto memberDto, Long board_id){
        if (comment == null){
            return ErrorDto.builder().StatusCode(400).Message("comment is null").build();
        }
        if(!userRepository.existsById(memberDto.getUser_id())) {
            return ErrorDto.builder().StatusCode(404).Message("member not found").build();
        }
        if(!boardRepository.existsById(board_id)) {
            return ErrorDto.builder().StatusCode(404).Message("").build();
        }
        var memberEntity = userRepository.findById(memberDto.getUser_id());
        var boardEntity = boardRepository.findById(board_id);
        if (memberEntity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("member not found").build();
        }
        if (boardEntity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("board not found").build();
        }
        var entity= BoardCommentEntity.builder()
                .user(memberEntity.get())
                .board(boardEntity.get())
                .uptime(new Date().getTime())
                .content(comment)
                .build();
        repository.saveAndFlush(entity);
        return ErrorDto.Empty();
    }
}
