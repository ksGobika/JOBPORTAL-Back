package com.jobportal.repository;

import com.jobportal.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByEmployerId(String employerId);
}
