package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ChatImageResponse;
import com.catering_app.Catering_app.dto.ChatRoomDTO;
import com.catering_app.Catering_app.dto.MessageReq;
import com.catering_app.Catering_app.model.Message;
import com.catering_app.Catering_app.service.chatService.ChatMessageService;
import com.catering_app.Catering_app.service.chatService.ChatRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatMessageService chatMessageService, ChatRoomService chatRoomService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
    }



    @MessageMapping("/send-message")
    public void sentMessage(@Payload MessageReq message) throws JsonProcessingException {
        try{
            Message saved = chatMessageService.sendMessage(message);
            String jsonMessage = new ObjectMapper().writeValueAsString(saved);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getChatRoomName(), jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/sent-image")
    public ResponseEntity<ChatImageResponse> sentMessage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(chatMessageService.uploadChatImageUrl(file));
    }
    @MessageMapping("/seen")
    public void seenMessage(@Payload String message){
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(message);
            Long messageId = Long.valueOf(jsonNode.get("messageId").asText());
            chatMessageService.markAsSeen(messageId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/get-messages")
    public List<Message> getMessages(@RequestParam("chatRoomName") String chatRoomName) {
            return chatMessageService.findChatRoom(chatRoomName);
    }

    @GetMapping("/chats")
    public List<ChatRoomDTO> getChatRoomByAdmin() {
        return chatRoomService.getAllChatRooms();
    }

}


