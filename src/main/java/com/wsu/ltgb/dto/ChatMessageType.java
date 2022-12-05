package com.wsu.ltgb.dto;

import lombok.Getter;

@Getter
public enum ChatMessageType {
    // 에러 400번대
    ERR_AUTH(401),
    ERR_CHAT( 402),

    // 채팅 200번대
    CHAT( 201),

    // 정보 전송 100번대
    CONNECT_SUCCESS(101),
    MEMBER_ENTER(102),
    MEMBER_LEAVE(103)
    ;

    private final int code;

    ChatMessageType(int code) {
        this.code = code;
    }
}
