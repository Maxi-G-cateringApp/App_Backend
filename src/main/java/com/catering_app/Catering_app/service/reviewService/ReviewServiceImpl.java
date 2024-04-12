package com.catering_app.Catering_app.service.reviewService;

import com.catering_app.Catering_app.dto.ReviewDto;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.Review;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.ReviewRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public boolean addReview(ReviewDto reviewDto) {
        Optional<User> optionalUser = authenticationService.getUserById(reviewDto.getUserId());
        Optional<Order> optionalOrders = orderService.getOrderById(reviewDto.getOrderId());

        if(optionalUser.isPresent() && optionalOrders.isPresent()){
            Review review = new Review();
            review.setReviewDate(new Date());
            review.setReview(reviewDto.getReview());
            review.setRating(reviewDto.getRating());
            review.setUser(optionalUser.get());
            review.setOrder(optionalOrders.get());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewByOrderId(UUID orderId) {
        Optional<Review> optionalReview = reviewRepository.findByOrderId(orderId);
        return optionalReview.orElse(null);
    }
}
