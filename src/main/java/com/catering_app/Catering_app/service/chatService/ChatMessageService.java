package com.catering_app.Catering_app.service.chatService;

import com.catering_app.Catering_app.dto.ChatImageResponse;
import com.catering_app.Catering_app.dto.MessageReq;
import com.catering_app.Catering_app.model.ChatRoom;
import com.catering_app.Catering_app.model.Message;
import com.catering_app.Catering_app.repository.ChatRoomRepository;
import com.catering_app.Catering_app.repository.MessageRepository;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final CloudinaryService cloudinaryService;

    public Message sendMessage(MessageReq message) {
        Optional<ChatRoom> optionalChatRoom = chatRoomService.getChatRoom(message.getChatRoomName());
        if (optionalChatRoom.isPresent()) {
            ChatRoom chatRoom = optionalChatRoom.get();
            return messageRepository.save(Message.builder()
                    .chatRoom(chatRoom)
                    .senderId(message.getSenderId())
                    .content(message.getContent())
                    .seen(false)
                    .timeStamp(generateTimeStamp())
                    .build());
        } else {
            return null;
        }
    }
    public ChatImageResponse uploadChatImageUrl(MultipartFile file) {
        Map<?, ?> uploadChatImage = cloudinaryService.uploadImage(file, "chat_image");
        ChatImageResponse chatImageResponse = new ChatImageResponse();
        chatImageResponse.setImageUrl((String) uploadChatImage.get("url"));
        return chatImageResponse;

    }

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

    public void markAsSeen(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                    ()-> new EntityNotFoundException("Not found"));
            message.setSeen(true);
            messageRepository.save(message);

    }

    public ChatImageResponse viewContentByMessageId(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            ChatImageResponse chatImageResponse = new ChatImageResponse();
            chatImageResponse.setImageUrl(message.getContent());
            return chatImageResponse;
        }else {
            throw new EntityNotFoundException();
        }
    }
}
