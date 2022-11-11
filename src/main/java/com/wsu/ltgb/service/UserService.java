package com.wsu.ltgb.service;

import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.dto.LoginDto;
import com.wsu.ltgb.dto.RegisterDto;
import com.wsu.ltgb.model.UserEntity;
import com.wsu.ltgb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;

    public Pair<ErrorDto, String> Login(LoginDto dto){
        if (dto == null){
            return Pair.of(ErrorDto.builder().StatusCode(400).Message("dto is null").build(), "");
        }
        var user = repository.login(dto.getId());
        if (user == null){
            return Pair.of(ErrorDto.builder().StatusCode(404).Message("login failed").build(), "");
        }
        var pw = new String(Base64.getDecoder().decode(user.getPassword()), StandardCharsets.UTF_8);
        if (!BCrypt.checkpw(dto.getPassword(), pw)){
            return Pair.of(ErrorDto.builder().StatusCode(404).Message("login failed").build(), "");
        }
        var res = jwtService.CreateToken(user);
        if (!res.getFirst().IsEmpty()) {
            return Pair.of(res.getFirst(), "");
        }
        return  Pair.of(ErrorDto.Empty(), res.getSecond());
    }

    public ErrorDto Register(RegisterDto dto){
        if (dto == null){
            return ErrorDto.builder().StatusCode(400).Message("dto is null").build();
        }
        if (!Idcheck(dto.getId()) || !NicknameCheck(dto.getNickName())){
            return ErrorDto.builder().StatusCode(409).Message("check nickname or id").build();
        }
        var password = Base64.getEncoder().encodeToString(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()).getBytes());
        var entity = UserEntity.builder()
                .id(dto.getId())
                .password(password)
                .nickname(dto.getNickName())
                .phone(dto.getPhone())
                .uptime(new Date().getTime())
                .build();
        repository.saveAndFlush(entity);
        return null;
    }

    public Boolean Idcheck(String id){
        if (id == null){
            return false;
        }
        return repository.idCheck(id) == null;
    }

    public Boolean NicknameCheck(String nickname){
        if (nickname == null){
            return false;
        }
        return repository.nicknameCheck(nickname) == null;
    }

}