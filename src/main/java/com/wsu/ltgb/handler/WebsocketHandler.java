package com.wsu.ltgb.handler;

import com.wsu.ltgb.dto.ChatMemberDto;
import com.wsu.ltgb.dto.ErrorDto;
import com.wsu.ltgb.service.ChatService;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    @Autowired
    ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        chatService.onConnected(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatService.onClosedConnection(session);
    }


}
