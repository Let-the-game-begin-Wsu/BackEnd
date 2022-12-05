package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDto {
    private final ChatMessageType messageType;
    private final int messageTypeCode;
    private final MemberDto member;
    private final Long uptime;
    private final Long roomId;
    private final Object Message;
}
