package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage( ChatMessage chatMessage) { //Payload 有效载荷的意思，便于理解
        return chatMessage;
    }
//@Payload

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    @RequestMapping("/manyman")
    public String manyman(){
        return "index";
    }
    @RequestMapping("/onetoone")
    public String onetoone(){
        return "OneToOne";
    }

    @RequestMapping("/baiduYunPan")
    public String baiduYunPan(){
        return "baiduYunPan";
    }

    @RequestMapping("/")
    public String home(){
        return "home";
    }

}
