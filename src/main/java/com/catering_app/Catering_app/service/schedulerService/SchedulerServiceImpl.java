package com.catering_app.Catering_app.service.schedulerService;

import com.catering_app.Catering_app.model.Notifications;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.Status;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.NotificationRepository;
import com.catering_app.Catering_app.repository.OrderRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    private final Duration inactiveTimePeriod = Duration.ofMinutes(3);

    @Override
    @Scheduled(cron = "0 0/3 * * * *")
    public void deleteUnverifiedUser() {

        LocalDateTime currentTime = LocalDateTime.now();
        List<User> unVerifiedUsers = userRepository.findByActive(false);

        for (User user : unVerifiedUsers) {
            LocalDateTime registrationTimeOfUser = user.getRegisterDateTime();
            Duration timeElapsed = Duration.between(registrationTimeOfUser, currentTime);
            if (timeElapsed.compareTo(inactiveTimePeriod) > 0) {
                userRepository.delete(user);
            }
        }
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void changeStatus() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if (order.getOrderedItems() == null || order.getUserLocation() == null) {
                Notifications notifications = notificationRepository.getNotificationByOrderId(order.getId());
                notificationRepository.delete(notifications);
                orderRepository.delete(order);
            }
        }
    }

}

