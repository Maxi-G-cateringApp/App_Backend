package com.catering_app.Catering_app.service.notifications;

import com.catering_app.Catering_app.model.Notifications;
import com.catering_app.Catering_app.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notifications> getAllNotifications(){
        return notificationRepository.findAll().stream()
                .sorted(Comparator
                        .comparing(Notifications::getDate)
                        .reversed()).toList();
    }
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    public void viewNotification(Long id) {
        Optional<Notifications>notificationsOptional = notificationRepository.findById(id);
        if (notificationsOptional.isPresent()){
            Notifications notifications = notificationsOptional.get();
            notifications.setOpen(true);
            notificationRepository.save(notifications);
        }else{
            throw new EntityNotFoundException();
        }
    }
}
