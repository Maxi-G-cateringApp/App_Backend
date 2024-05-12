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
        try{
            Message saved = chatMessageService.sendMessage(message);
            String jsonMessage = new ObjectMapper().writeValueAsString(saved);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getChatRoomName(), jsonMessage);
        } catch (Exception e) {
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





















//    @MessageMapping("/send-message")
//    public void sentMessage(@Payload ChatMessageData chatMessageData) throws IOException {
//        try{
//            System.out.println(chatMessageData.getContent());
//            System.out.println(chatMessageData.getType()+"  type");
//            if("text".equals(chatMessageData.getType())) {
//                String chatRoomName = chatMessageData.getChatRoomName();
//                String senderId = chatMessageData.getSenderId();
//                String content = chatMessageData.getContent();
//                MessageReq message = new MessageReq(chatRoomName,senderId,content);
//                Message saved = chatMessageService.sendMessage(message);
//                String jsonMessage = new ObjectMapper().writeValueAsString(saved);
//                simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getChatRoomName(), jsonMessage);
//            }else if("file".equals(chatMessageData.getType())){
//                byte[] fileData = chatMessageData.getFile().getBytes();
//                String chatRoomName = chatMessageData.getChatRoomName();
//                String senderId = chatMessageData.getSenderId();
//                MessageReq message = new MessageReq(chatRoomName,senderId,new String(fileData));
//                Message saved = chatMessageService.sendMessage(message);
//                String jsonMessage = new ObjectMapper().writeValueAsString(saved);
//                simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getChatRoomName(), jsonMessage);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
