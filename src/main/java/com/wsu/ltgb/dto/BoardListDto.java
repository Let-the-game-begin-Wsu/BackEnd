package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BoardListDto {
    private final long boardCount;
    private final long pageIndex;
    private final List<BoardListItemDto> items;
}
