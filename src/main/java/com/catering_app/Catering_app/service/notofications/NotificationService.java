package com.catering_app.Catering_app.service.notofications;

import com.catering_app.Catering_app.model.Notifications;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessageToAdmin(String message){
        Notifications notifications = new Notifications();
        notifications.setMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/notification",notifications);

    }

}
