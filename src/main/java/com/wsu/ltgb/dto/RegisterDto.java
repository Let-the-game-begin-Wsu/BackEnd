package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private final String id;
    private final String password;
    private final String nickName;
    private final String phone;
}
