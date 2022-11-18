package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentListDto {
    private final long CommentCount;
    private final long pageIndex;
    private final List<CommentListItemDto> items;
}
