package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatRoomName;
    private String participant1;
    private String participant2;

//    public String getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(String senderId) {
//        this.senderId = senderId;
//    }
//
//    public String getRecipientId() {
//        return recipientId;
//    }
//
//    public void setRecipientId(String recipientId) {
//        this.recipientId = recipientId;
//    }
//
//    public ChatRoom() {}
//
//    public ChatRoom(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
