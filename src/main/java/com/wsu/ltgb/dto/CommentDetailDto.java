package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDetailDto {
    private final CommentDetailUserDto user;
    private final CommentDetailContentDto content;

    public Boolean isEmpty() {
        return user == null && content == null;
    }
}
