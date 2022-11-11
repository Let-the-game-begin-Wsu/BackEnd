package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardDetailDto {
    private final long id;
    private final String title;
    private final String content;
    private final long uptime;
}
