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

    public Pair<ErrorDto, CommentListDto>GetCommentList(long boardId, int listLimit, int pageIndex){
        var offset = 0;
        if(pageIndex>1){
            offset = (pageIndex -1) * listLimit;
        }
        var entityArrayList = repository.GetCommentList(boardId, listLimit, offset);
        var list = entityArrayList.stream().map(
                x->CommentListItemDto.builder()
                        .userId(x.getUser().getUserId())
                        .uptime(x.getUptime())
                        .id(x.getBoard().getBoardId())
                        .content(x.getContent())
                        .nickName(x.getUser().getNickname())
                        .build()
        ).toList();
        var count=repository.GetCommentCount(boardId);
        var response=CommentListDto.builder().CommentCount(count).items(list).pageIndex(pageIndex).build();
        return Pair.of(ErrorDto.Empty(),response);
    }
    public ErrorDto UpdateComment(MemberDto memberDto, CommentRequestDto requestDto){
        var entity = repository.findById(requestDto.getContent_id());
        if (entity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("board not found").build();
        }
        var result = entity.get();
        if (!memberDto.getUser_id().equals(result.getUser().getUserId())){
            return ErrorDto.builder().StatusCode(403).Message("bad auth").build();
        }
        result.setContent(requestDto.getContent());
        repository.saveAndFlush(result);
        return ErrorDto.Empty();
    }
    public ErrorDto RemoveComment(MemberDto memberDto, Long boardCommentId){
        var entity = repository.findById(boardCommentId);
        if (entity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("boardComment not found").build();
        }
        if (!memberDto.getUser_id().equals(entity.get().getUser().getUserId())){
            return ErrorDto.builder().StatusCode(403).Message("bad auth").build();
        }
        repository.deleteById(boardCommentId);
        return ErrorDto.Empty();
    }

}
