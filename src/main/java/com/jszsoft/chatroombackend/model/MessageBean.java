package com.jszsoft.chatroombackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageBean {
    private String type;
    private String name;
    private String message;
    private String timestamp;
}
