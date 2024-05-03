package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByChatRoomId(Long id);

//    List<Message>findBySenderIdAndRecipientId(String userId, String adminId);

//    List<Message>findAllByChatId(Long id);


}
