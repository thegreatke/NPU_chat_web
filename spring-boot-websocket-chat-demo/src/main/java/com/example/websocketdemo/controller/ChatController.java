package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by great_KE on 24/07/17.
 */
@Controller
public class ChatController {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;//可以实现自由的向任意目的地发送消息，并且订阅此目的地的所有用户都能收到消息。


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage( @Payload ChatMessage chatMessage) { //Payload 有效载荷的意思，便于理解
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


    //实现单聊
    @MessageMapping("/chat.sendMessageOneLine")
//    @SendTo("/topic/public")
    public void sendMessageOneLine( @Payload ChatMessage chatMessage) { //Payload 有效载荷的意思，便于理解
        String destination = "/topic/" + chatMessage.getRoom();

        messagingTemplate.convertAndSend(destination, chatMessage);

    }


    @MessageMapping("/chat.addUserOneLine")
    public void addUserOneLine(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("room", chatMessage.getRoom());
        String destination = "/topic/" + chatMessage.getRoom();

        messagingTemplate.convertAndSend(destination, chatMessage);

    }



    @RequestMapping("/chat")
    public String manyman(){
        return "chat";
    }


    @RequestMapping("/index")
    public String home(){
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/forum")
    public String forum(){
        return "forum";
    }


    @RequestMapping("/foruminner")
    public String foruminner(){
        return "foruminner";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }



    @RequestMapping("/test3")

    public String test3(){
        return "test3";
    }






}
