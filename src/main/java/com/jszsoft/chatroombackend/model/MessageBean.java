package com.jszsoft.chatroombackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageBean {
    private String type;
    private String name;
    private String message;
    private String timestamp;
}
