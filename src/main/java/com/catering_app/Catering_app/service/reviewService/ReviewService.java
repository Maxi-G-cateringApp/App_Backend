package com.catering_app.Catering_app.service.reviewService;

import com.catering_app.Catering_app.dto.ReviewDto;
import com.catering_app.Catering_app.model.Review;

import java.util.UUID;

public interface ReviewService {

    boolean addReview(ReviewDto reviewDto);
    Review getReviewByOrderId(UUID orderId);
}
