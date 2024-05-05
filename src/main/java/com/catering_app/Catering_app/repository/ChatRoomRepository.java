package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findByChatRoomName(String chatRoomName);

}
