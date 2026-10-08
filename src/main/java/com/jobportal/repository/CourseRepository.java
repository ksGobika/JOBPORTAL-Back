package com.jobportal.repository;

import com.jobportal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByEmployerId(String employerId);
    List<Course> findByCategory(String category);
    List<Course> findByStatus(String status);
}

