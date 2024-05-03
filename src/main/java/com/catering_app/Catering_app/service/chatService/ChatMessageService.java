package com.catering_app.Catering_app.service.chatService;

import com.catering_app.Catering_app.dto.MessageReq;
import com.catering_app.Catering_app.model.ChatRoom;
import com.catering_app.Catering_app.model.Message;
import com.catering_app.Catering_app.repository.ChatRoomRepository;
import com.catering_app.Catering_app.repository.MessageRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public Message sendMessage(MessageReq message) {
        Optional<ChatRoom> optionalChatRoom = chatRoomService.getChatRoom(message.getChatRoomName());
        if (optionalChatRoom.isPresent()) {
            ChatRoom chatRoom = optionalChatRoom.get();
            return messageRepository.save(Message.builder()
                    .chatRoom(chatRoom)
                    .content(message.getContent())
                    .senderId(message.getSenderId())
                    .timeStamp(generateTimeStamp())
                    .build());
        } else {
            return null;
        }

    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";
        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }
        return date + "-" + time;

    }


//    public List<Message> findChatMessages(UUID userId,UUID adminId){
//        String user = userId.toString();
//        String admin = adminId.toString();
//        return messageRepository.findBySenderIdAndRecipientId(user,admin);
//    }

//    public List<Message> findChatRoom(String chatRoomName) {
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
//        if (optionalChatRoom.isPresent()) {
//            return messageRepository.findByChatRoomId(optionalChatRoom.get().getId());
//        } else {
//            chatRoomService.createChatRoom(chatRoomName);
//            return new ArrayList<>();
//        }
//    }

    public List<Message> findChatRoom(String chatRoomName) {
        synchronized (this) {
            Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
            if (optionalChatRoom.isEmpty()) {
                chatRoomService.createChatRoom(chatRoomName);
                return new ArrayList<>();
            } else {
                return messageRepository.findByChatRoomId(optionalChatRoom.get().getId());
            }
        }
    }

//    public List<Message> findChatRoom(String chatRoomName) {
//        List<ChatRoom> chatRooms = chatRoomRepository.findByChatRoomName(chatRoomName);
//        List<Message> messages = new ArrayList<>();
//        if(chatRooms.isEmpty()) {
//            chatRoomService.createChatRoom(chatRoomName);
//        } else {
//            for (ChatRoom chatRoom : chatRooms) {
//                messages.addAll(messageRepository.findByChatRoomId(chatRoom.getId()));
//            }
//        }
//        return messages;
//    }


//}
//    public List<Message> findChatRoom(String chatRoomName) {
//        Optional<ChatRoom> chatRooms = chatRoomRepository.findByChatRoomName(chatRoomName);
//        List<Message> messages = new ArrayList<>();
//
//        if (chatRooms.isPresent()) {
//            for (ChatRoom chatRoom : chatRooms.get()) {
//                List<Message> roomMessages = messageRepository.findByChatRoomId(chatRoom.getId());
//                messages.addAll(roomMessages);
//            }
//        } else {
//            ChatRoom newChatRoom = chatRoomService.createChatRoom(chatRoomName);
//            messages = messageRepository.findByChatRoomId(newChatRoom.getId());
//        }
//
//        return messages;
//    }
}
