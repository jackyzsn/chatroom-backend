package com.jszsoft.chatroombackend;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.jszsoft.chatroombackend.model.MessageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketChatEventListener {
    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            MessageBean chatMessage = new MessageBean();
            chatMessage.setType("event-leave");
            chatMessage.setName(username);
            chatMessage.setTimestamp(formatter.format(new Date()));
            Gson gson = new Gson();

            messagingTemplate.convertAndSend("/topic/user", gson.toJson(chatMessage));
        }
    }
}
