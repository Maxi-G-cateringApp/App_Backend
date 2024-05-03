package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ChatRoomDTO;
import com.catering_app.Catering_app.dto.MessageReq;
import com.catering_app.Catering_app.model.Message;
import com.catering_app.Catering_app.service.chatService.ChatMessageService;
import com.catering_app.Catering_app.service.chatService.ChatRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;


    @MessageMapping("/send-message")
    public void sentMessage(@Payload MessageReq message) throws JsonProcessingException {
        System.out.println(message.getChatRoomName()+" chat room");
        System.out.println(message.getContent()+"  content");
        Message saved = chatMessageService.sendMessage(message);
        String jsonMessage = new ObjectMapper().writeValueAsString(saved);
        simpMessagingTemplate.convertAndSend("/topic/messages/"+message.getChatRoomName(),jsonMessage);
    }


    @PostMapping("/get-messages")
    public List<Message> getMessages(@RequestParam("chatRoomName") String chatRoomName) {
        return chatMessageService.findChatRoom(chatRoomName);

    }

    @GetMapping("/chats")
    public List<ChatRoomDTO>getChatRoomByAdmin(){
        return chatRoomService.getAllChatRooms();
    }

}
