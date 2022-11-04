package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.dto.LoginDto;
import com.wsu.ltgb.dto.RegisterDto;
import com.wsu.ltgb.model.UserEntity;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public String Login(LoginDto dto){
        var user = repository.login(dto.getID(), dto.getPassword());
        if (user == null){
            return "실패";
        }
        return "성공";
    }

    public ErrorDto Register(RegisterDto dto){
        if (dto == null){
            return ErrorDto.builder().StatusCode(400).Message("dto is null").build();
        }
        var password = Base64.getEncoder().encodeToString(BCrypt.hashpw(dto.Password, BCrypt.gensalt()).getBytes());
        var entity = UserEntity.builder()
                .id(dto.Id)
                .password(password)
                .nickname(dto.NickName)
                .phone(dto.Phone)
                .build();
        repository.saveAndFlush(entity);
        return null;
    }

}
