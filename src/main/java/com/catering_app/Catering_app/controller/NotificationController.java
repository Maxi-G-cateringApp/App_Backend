package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.model.Notifications;
import com.catering_app.Catering_app.service.notifications.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/notifications")
    public ResponseEntity<List<Notifications>>getAllNotifications(){
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @DeleteMapping("/delete/notification")
    public ResponseEntity<?>deleteNotification(@RequestParam Long id){
        notificationService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/view/notification")
    public ResponseEntity<?>viewNotification(@RequestParam Long id){
        notificationService.viewNotification(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
