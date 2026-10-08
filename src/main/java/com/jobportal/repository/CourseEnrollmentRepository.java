package com.jobportal.repository;

import com.jobportal.model.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, String> {
    List<CourseEnrollment> findBySeekerId(String seekerId);
    List<CourseEnrollment> findByEmployerId(String employerId);
    List<CourseEnrollment> findByCourseId(String courseId);
    Optional<CourseEnrollment> findByCourseIdAndSeekerId(String courseId, String seekerId);
}

