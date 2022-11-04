package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    public final int StatusCode;
    public final String Message;
}
