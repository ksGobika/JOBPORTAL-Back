package com.jobportal.controller;

import com.jobportal.model.Review;
import com.jobportal.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Review> getReviews(@RequestParam(required = false) String employerId) {
        if (employerId != null && !employerId.trim().isEmpty()) {
            return reviewRepository.findByEmployerId(employerId);
        }
        return reviewRepository.findAll();
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        if (review.getId() == null || review.getId().trim().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
        }
        if (review.getCreatedAt() == null || review.getCreatedAt().trim().isEmpty()) {
            review.setCreatedAt(new Date().toString());
        }
        return reviewRepository.save(review);
    }
}
