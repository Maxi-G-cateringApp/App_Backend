package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {

    Notifications getNotificationByOrderId(UUID orderId);
}
