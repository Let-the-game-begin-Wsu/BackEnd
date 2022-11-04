package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    public final Integer StatusCode;
    public final String Message;

    public static ErrorDto Empty(){
        return new ErrorDto(null, null);
    }

    public boolean IsEmpty() {
        return this.Message == null && this.StatusCode == null;
    }
}
