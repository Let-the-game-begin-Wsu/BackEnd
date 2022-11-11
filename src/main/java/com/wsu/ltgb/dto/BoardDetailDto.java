package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardDetailDto {
    private final BoardDetailUserDto user;
    private final BoardDetailContentDto content;

    public Boolean isEmpty() {
        return user == null && content == null;
    }
}
