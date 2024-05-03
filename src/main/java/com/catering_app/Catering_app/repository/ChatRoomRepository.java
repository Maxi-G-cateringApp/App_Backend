package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.ChatRoom;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
//    ChatRoom findByName(String name);
//    ChatRoom findById(String id);

//    Optional<ChatRoom> findBySenderIdAndRecipientId(UUID senderId, UUID recipientId);

//    List<ChatRoom> findByRecipientId(UUID adminId);


//    Optional<ChatRoom> findByParticipant1AndParticipant2(String participant1, String participant2);

    Optional<ChatRoom> findByChatRoomName(String chatRoomName);



//    List<ChatRoom> findByRecipientId(String user);
//    Optional<ChatRoom> findFirstByParticipantsContaining(List<UUID> participants);
//    ChatRoom findByParticipantsContaining(List<UUID> participants);


//    List<ChatRoom> findByRecipientId(String adminId);

}





//    HashSet<ChatRoom>getChatByAdminId(UUID adminId);
//    HashSet<ChatRoom>getChatByUserId(UUID userId);
//    HashSet<ChatRoom>getChatByUserIdAndAdminId(UUID userId, UUID adminId);
//    HashSet<ChatRoom>getChatByAdminIdAndUserId(UUID userId, UUID adminId);

