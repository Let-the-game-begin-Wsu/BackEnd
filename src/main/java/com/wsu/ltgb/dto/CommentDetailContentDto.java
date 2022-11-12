package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDetailContentDto {
    private final long id;
    private final String content;
    private final long uptime;
}
