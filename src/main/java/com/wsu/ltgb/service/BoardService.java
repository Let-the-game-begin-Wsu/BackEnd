package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.BoardRequestDto;
import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.dto.MemberDto;
import com.wsu.ltgb.model.BoardEntity;
import com.wsu.ltgb.persistence.BoardRepository;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;
    @Autowired
    private UserRepository userRepository;

    public ErrorDto CreateBoard(MemberDto memberDto, BoardRequestDto requestDto) {
        if (memberDto.IsEmpty()){
            return ErrorDto.builder().StatusCode(403).Message("bad token").build();
        }
        if (requestDto == null){
            return ErrorDto.builder().StatusCode(400).Message("bad req").build();
        }
        var memberEntity = userRepository.findById(memberDto.getUser_id());
        if (memberEntity.isPresent()){
            return ErrorDto.builder().StatusCode(403).Message("bad token").build();
        }

        var entity = BoardEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getComment())
                .user(memberEntity.get())
                .uptime(new Date().getTime())
                .build();

        repository.saveAndFlush(entity);
        return ErrorDto.Empty();
    }
}
