package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.ReviewDto;
import com.catering_app.Catering_app.service.reviewService.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add-review")
    public ResponseEntity<?>addReview(@RequestBody ReviewDto reviewDto){
        boolean success = reviewService.addReview(reviewDto);
        if (success){
            return ResponseEntity.ok(new ResponseDto(true,"rated"));
        }else {
            return ResponseEntity.ok(new ResponseDto(false,"error"));
        }
    }

    @GetMapping("/get-review/{orderId}")
    public ResponseEntity<?>getReviewByOrderId(@PathVariable("orderId") UUID orderId){
        return ResponseEntity.ok(reviewService.getReviewByOrderId(orderId));
    }
}
