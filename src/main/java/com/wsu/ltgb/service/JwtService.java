package com.wsu.ltgb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.dto.MemberDto;
import com.wsu.ltgb.model.UserEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String KEY = "JjxgVlZaek5XaVFsMDZyPlxaJ3ZqQE4pdg";

    public Pair<ErrorDto, String> CreateToken(UserEntity user){
        if (user == null
                || user.getUser_id() <= 0
                || user.getNickname() == null
        ){
            return Pair.of(ErrorDto.builder().StatusCode(404).Message("user not found").build(), "");
        }

        var date = new Date();
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        var token = JWT.create()
                .withIssuedAt(date)
                .withExpiresAt(new Date(date.getTime() + 86400000))
                .withClaim("user_id", user.getUser_id())
                .withClaim("nickname", user.getNickname())
                .withClaim("image", user.getImage())
                .sign(algorithm);
        return  Pair.of(ErrorDto.Empty(), token);
    }

    public Pair<ErrorDto, MemberDto> ValidateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(KEY); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .acceptExpiresAt(new Date().getTime())
                .build(); //Reusable verifier instance
        DecodedJWT jwt;
        try{
            jwt = verifier.verify(token);
        }catch (Exception ex){
            return Pair.of(ErrorDto.builder().StatusCode(403).Message("bad token").build(), MemberDto.Empty());
        }
        var member = MemberDto.builder()
                .user_id(jwt.getClaim("user_id").asLong())
                .image(jwt.getClaim("image").asString())
                .nickname(jwt.getClaim("nickname").asString())
                .build();
        return Pair.of(ErrorDto.Empty(), member);
    }

}
