package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    public final String Id;
    public final String Password;
    public final String NickName;
    public final String Phone;
}
