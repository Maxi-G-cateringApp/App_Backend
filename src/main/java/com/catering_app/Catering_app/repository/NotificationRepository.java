package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}
