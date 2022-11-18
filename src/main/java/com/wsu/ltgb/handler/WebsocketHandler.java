package com.wsu.ltgb.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;

@Component
public class WebsocketHandler extends TextWebSocketHandler {

    private static final HashSet<WebSocketSession> userList = new HashSet<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (var user: userList) {
            user.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userList.add(session);
    }
}
