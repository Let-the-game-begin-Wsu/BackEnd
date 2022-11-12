package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardTopicResponseDto {
    private final long allCount;
    private final long pageIndex;
    private final List<BoardTopicDto> items;
}
