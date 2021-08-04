package com.jszsoft.chatroombackend.web;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jszsoft.chatroombackend.model.MessageBean;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @MessageMapping("/user-all")
    @SendTo("/topic/user")
    public MessageBean sendToAll(@Payload MessageBean message) {
        message.setTimestamp(formatter.format(new Date()));

        return message;
    }

    @MessageMapping("/event")
    @SendTo("/topic/user")
    public MessageBean newUser(@Payload MessageBean webSocketChatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getName());
        webSocketChatMessage.setTimestamp(formatter.format(new Date()));

        return webSocketChatMessage;
    }
}
