package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;
    private String senderId;
    private String content;
    private String timeStamp;


}
