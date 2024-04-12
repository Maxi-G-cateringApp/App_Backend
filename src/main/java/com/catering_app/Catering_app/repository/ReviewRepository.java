package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review>findByOrderId(UUID orderId);


}
