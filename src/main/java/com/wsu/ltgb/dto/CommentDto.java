package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    public final Long user_id;
    public final String user_nick;
    public final String user_image;
    public final String content;
}
