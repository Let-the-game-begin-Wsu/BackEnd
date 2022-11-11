package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequestDto {
    private final String title;
    private final String comment;
}
