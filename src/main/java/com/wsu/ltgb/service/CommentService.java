package com.wsu.ltgb.service;

import com.wsu.ltgb.controller.CommentController;
import com.wsu.ltgb.dto.*;
import com.wsu.ltgb.model.BoardCommentEntity;
import com.wsu.ltgb.model.BoardEntity;
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
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    public ErrorDto Comment(CommentDto commentDto, MemberDto memberDto){
        if (commentDto == null){
            return ErrorDto.builder().StatusCode(400).Message("dto is null").build();
        }
        if(!userRepository.existsById(memberDto.getUser_id())) {
            var err =ErrorDto.builder().StatusCode(404).Message("not user_id").build();
            return err;
        }
        var memberEntity = userRepository.getReferenceById(memberDto.getUser_id());
//        if (memberEntity.isPresent()){
//            return ErrorDto.builder().StatusCode(403).Message("bad token").build();
//        }
        var entity= BoardCommentEntity.builder()
                .user(memberEntity)
                .content(commentDto.getContent())
                .uptime(new Date().getTime())
                .build();
        repository.saveAndFlush(entity);
        return null;
    }
    public Pair<ErrorDto, CommentDetailDto>GetComment(long boardId){
        if(repository.existsById(boardId)){
            var err = ErrorDto.builder().StatusCode(404).Message("board not found").build();
            return Pair.of(err, CommentDetailDto.builder().build());
        }
//        if(!boardRepository.existsById(commentDto.board_id)) {
//            var err =ErrorDto.builder().StatusCode(404).Message("").build();
//            return err;
//        }
        var entity = repository.getReferenceById(boardId);
        var boardCommentEntity = entity.getBoard();
        var user = CommentDetailUserDto.builder()
                .id(boardCommentEntity.getBoard_id())
                .build();
        var content=CommentDetailContentDto.builder()
                .content(entity.getContent())
                .uptime(entity.getUptime())
//                .id(entity.getBoard_comment_id())
                .build();
        var response = CommentDetailDto.builder()
                .content(content)
                .user(user)
                .build();
        return Pair.of(ErrorDto.Empty(), response);
    }

}
