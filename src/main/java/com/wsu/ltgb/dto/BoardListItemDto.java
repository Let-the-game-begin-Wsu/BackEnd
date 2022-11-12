package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardListItemDto {
    private final long id;
    private final String title;
    private final long userId;
    private final String userNick;
    private final long uptime;
}
