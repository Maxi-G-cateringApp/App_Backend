package com.catering_app.Catering_app.service.chatService;

import com.catering_app.Catering_app.dto.ChatRoomDTO;
import com.catering_app.Catering_app.model.ChatRoom;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.ChatRoomRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final AuthenticationService authenticationService;

    public void createChatRoom(String chatRoomName) {
        String[] participants = chatRoomName.split("_");
        if(participants[0].equals(participants[1])) {
            throw new RuntimeException("Cannot create chat room with the same participants");
        }else{
            chatRoomRepository.save(ChatRoom.builder()
                    .chatRoomName(chatRoomName)
                    .participant1(participants[0])
                    .participant2(participants[1])
                    .build());
        }
    }

    public Optional<ChatRoom> getChatRoom(String chatRoomName) {
        return chatRoomRepository.findByChatRoomName(chatRoomName);
    }

    public List<ChatRoomDTO> getAllChatRooms() {
        User admin = authenticationService.getAdmin();
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();

        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();
        for (ChatRoom chatRoom : chatRoomList) {
            Optional<User> participant;
            if (chatRoom.getParticipant1().equals(admin.getId().toString())) {
                participant = authenticationService.getUserById(UUID.fromString(chatRoom.getParticipant2()));
            } else {
                participant = authenticationService.getUserById(UUID.fromString(chatRoom.getParticipant1()));
            }
            if (participant.isPresent()) {
                ChatRoomDTO chatRoomDTO = new ChatRoomDTO(chatRoom.getId(), chatRoom.getChatRoomName(), participant.get());
                chatRoomDTOList.add(chatRoomDTO);
            }
        }
        return chatRoomDTOList;
    }

}
