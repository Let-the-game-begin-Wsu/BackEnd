package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    public final String id;
    public final String password;
    public final String nickName;
    public final String phone;
}
