package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentListItemDto {
    private final long id;
    private final String content;
    private final long userId;
    private final long uptime;
    private final String nickName;
}
