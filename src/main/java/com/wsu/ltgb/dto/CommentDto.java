package com.wsu.ltgb.dto;

import lombok.Data;

@Data
public class CommentDto {
    public final long user_id;
    public final String content;
    public final long board_id;
    public final long board_comment_id;
}
