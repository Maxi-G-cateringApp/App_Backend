//package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
//public class WebSocketController {


//    @Autowired
//    private ChatDAO chatDAO;
//    @Autowired
//    private MessageDAO messageDAO;
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;


//    @MessageMapping("/chat/{roomId}")
//    @SendTo("/topic/{roomId}")
//    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message){
//        System.out.println(message+" message........");
//        System.out.println(roomId+"  room id");
//        return new ChatMessage(message.getMessage(),message.getUser());
//    }

//    @MessageMapping("/chat/{to}")
//    public void sendMessage(@DestinationVariable String to, MessageEntity message){
//       message.setChat_id(createAndOrGetChat(to));
//       message.setT_stamp(generateTimeStamp());
//       message = messageDAO.save(message);
//       simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
//
//    }

//    @PostMapping("/getChats")
//    public List<ChatEntity> getChats(@RequestBody String user){
//        return chatDAO.findByParticipant(user);
//    }

//    @PostMapping("/getMessages")
//    public List<MessageEntity>getMessages(@RequestBody String chat){
//        ChatEntity ce = chatDAO.findByName(chat);
//        if (ce != null){
//            return null;
//        }else {
//            return new ArrayList<MessageEntity>();
//        }
//
//    }





//}
