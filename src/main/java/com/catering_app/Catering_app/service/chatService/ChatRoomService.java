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


    public void createChatRoom(String chatRoomName){
        String [] participants = chatRoomName.split("_");
        chatRoomRepository.save(ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .participant1(participants[0])
                .participant2(participants[1])
                .build());
    }


    Optional<ChatRoom> getChatRoom(String chatRoomName){
        return chatRoomRepository.findByChatRoomName(chatRoomName);
    }

//    public List<ChatRoom> getAllChatRooms() {
//       return chatRoomRepository.findAll();
//    }
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


//    getRecipientId (userId: string)
//    {
//        let recId:string;
//        if (this.selectedChatRoom.participant1 !== userId ) {
//            recId = this.selectedChatRoom.participant1;
//        } else {
//            recId = this.selectedChatRoom.participant2;
//        }
//        return recId
//    }


//    public List<ChatRoom> getAdminChatRooms(UUID adminId) {
//        return chatRoomRepository.findByRecipientId(adminId);
//    }

//    public String getChatRoomId(UUID userId, UUID adminId) {
//        String userIdString = userId.toString();
//        String adminIdString = adminId.toString();
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findBySenderIdAndRecipientId(userId, adminId);
//        return optionalChatRoom.map(ChatRoom::getId).orElseGet(() -> {
//            ChatRoom chatRoom = createChatRoom(userId, adminId, userIdString + "_" + adminIdString));
//            return chatRoom.getId();
//        });
//    }
}

//    public Long getChatId(Message message) {
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(message.getId());
//        if (optionalChatRoom.isPresent()) {
//            return optionalChatRoom.get().getId();
//        } else {
//            var name =
//                    String.format("%s_%s", message.getSenderId(), message.getRecipientId());
//           ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
//                           .recipientId(message.getRecipientId())
//                           .senderId(message.getSenderId())
//                           .name(name)
//                   .build());
//
//
//            return chatRoomRepository.save(chatRoom).getId();
//        }
//    }
//    public String getChatRoomName(Message message){
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(message.getId());
//        return optionalChatRoom.map(ChatRoom::getName).orElse(null);
//
//    }
//}


//      return chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId)
//            .map(ChatRoom::getId)
//               .or(()->{
//        if (!createIfNotExist){
//            return Optional.empty();
//        }
//        ChatRoom senderRecipient = ChatRoom
//                .builder()
//                .senderId(senderId)
//                .recipientId(recipientId)
//                .build();
//        ChatRoom recipientSender = ChatRoom
//                .builder()
//                .senderId(recipientId)
//                .recipientId(senderId)
//                .build();
//        chatRoomRepository.save(senderRecipient);
//        chatRoomRepository.save(recipientSender);
//        return Optional.of(ChatRoom::getChatId);
//    });
//}
