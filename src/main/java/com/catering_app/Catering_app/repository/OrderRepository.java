package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Orders;
import com.catering_app.Catering_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    List<Orders> findByUser(User user);
}