package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.BoardTopicDto;
import com.wsu.ltgb.dto.BoardTopicResponseDto;
import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.dto.MemberDto;
import com.wsu.ltgb.model.BoardTopicEntity;
import com.wsu.ltgb.persistence.BoardTopicRepository;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BoardTopicService {
    @Autowired
    private BoardTopicRepository repository;
    @Autowired
    private UserRepository userRepository;

    public ErrorDto CreateTopic(MemberDto memberDto, BoardTopicDto request){
        var user = userRepository.getReferenceById(memberDto.getUser_id());
        var entity = BoardTopicEntity.builder()
                .title(request.getTitle())
                .image(request.getImage_url())
                .uptime(new Date().getTime())
                .description(request.getDescription())
                .build();
        if (!userRepository.existsById(user.getUserId())){
            return ErrorDto.builder().StatusCode(404).Message("user not found").build();
        }
        repository.saveAndFlush(entity);
        return ErrorDto.Empty();
    }

    public ErrorDto RemoveTopic(Long topicId){
        if(!userRepository.existsById(topicId)){
            return ErrorDto.builder().StatusCode(404).Message("topic not found").build();
        }
        repository.deleteById(topicId);
        return ErrorDto.Empty();
    }

    public ErrorDto UpdateTopic(MemberDto memberDto, BoardTopicDto request, Long topicId){
        var user = userRepository.getReferenceById(memberDto.getUser_id());
        var entity = repository.findById(topicId);
        if (entity.isEmpty()){
            return ErrorDto.builder().StatusCode(404).Message("topic not found").build();
        }
        if (!userRepository.existsById(user.getUserId())){
            return ErrorDto.builder().StatusCode(404).Message("user not found").build();
        }
        var updateEntity = entity.get();
        updateEntity.setUser(user);
        updateEntity.setTitle(request.getTitle());
        updateEntity.setDescription(request.getDescription());
        updateEntity.setImage(request.getImage_url());
        repository.saveAndFlush(updateEntity);
        return ErrorDto.Empty();
    }

    public BoardTopicResponseDto GetList(int limit, int pageIndex){
        var offset = 0;
        if (pageIndex > 1){
            offset = (pageIndex -1) * limit;
        }
        var entityList = repository.GetTopicList(limit, offset);
        var items = entityList.stream().map(
                x -> BoardTopicDto.builder()
                        .image_url(x.getImage())
                        .title(x.getTitle())
                        .description(x.getDescription())
                        .build()
        ).toList();
        var count = repository.GetTopicCount();
        return BoardTopicResponseDto.builder()
                .allCount(count)
                .pageIndex(pageIndex)
                .items(items)
                .build();
    }
}

