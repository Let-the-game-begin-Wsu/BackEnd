package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardTopicDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String image_url;
}
