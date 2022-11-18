package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ChatMemberDto {
    private final UUID websocketToken;
    private final long userId;
    private final String userNickname;
    private final String userImage;
    private final WebSocketSession session;
    private final List<Long> enteredRoomIds;
}
