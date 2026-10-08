package com.jobportal.controller;

import com.jobportal.model.Course;
import com.jobportal.model.CourseEnrollment;
import com.jobportal.repository.CourseEnrollmentRepository;
import com.jobportal.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/enrollments")
public class CourseEnrollmentController {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public CourseEnrollmentController(CourseEnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<CourseEnrollment> getEnrollments(
            @RequestParam(required = false) String seekerId,
            @RequestParam(required = false) String employerId,
            @RequestParam(required = false) String courseId
    ) {
        if (courseId != null && seekerId != null) {
            Optional<CourseEnrollment> opt = enrollmentRepository.findByCourseIdAndSeekerId(courseId, seekerId);
            return opt.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        if (seekerId != null && !seekerId.trim().isEmpty()) {
            return enrollmentRepository.findBySeekerId(seekerId);
        }
        if (employerId != null && !employerId.trim().isEmpty()) {
            return enrollmentRepository.findByEmployerId(employerId);
        }
        if (courseId != null && !courseId.trim().isEmpty()) {
            return enrollmentRepository.findByCourseId(courseId);
        }
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEnrollment> getEnrollmentById(@PathVariable String id) {
        return enrollmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseEnrollment> enrollCourse(@RequestBody CourseEnrollment enrollment) {
        if (enrollment.getCourseId() == null || enrollment.getSeekerId() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if already enrolled
        Optional<CourseEnrollment> existing = enrollmentRepository.findByCourseIdAndSeekerId(
                enrollment.getCourseId(), enrollment.getSeekerId()
        );
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        }

        if (enrollment.getId() == null || enrollment.getId().trim().isEmpty()) {
            enrollment.setId("enr-" + UUID.randomUUID().toString().substring(0, 8));
        }
        if (enrollment.getEnrolledAt() == null || enrollment.getEnrolledAt().trim().isEmpty()) {
            enrollment.setEnrolledAt(new Date().toString());
        }
        if (enrollment.getProgress() == null) {
            enrollment.setProgress(0);
        }
        if (enrollment.getStatus() == null) {
            enrollment.setStatus("enrolled");
        }

        // Increment enrolledCount on Course
        courseRepository.findById(enrollment.getCourseId()).ifPresent(c -> {
            int current = c.getEnrolledCount() != null ? c.getEnrolledCount() : 0;
            c.setEnrolledCount(current + 1);
            courseRepository.save(c);
        });

        CourseEnrollment saved = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(saved);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<CourseEnrollment> updateEnrollment(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        Optional<CourseEnrollment> opt = enrollmentRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CourseEnrollment enrollment = opt.get();

        if (updates.containsKey("progress") && updates.get("progress") != null) {
            enrollment.setProgress(Integer.parseInt(updates.get("progress").toString()));
        }
        if (updates.containsKey("completedLessons") && updates.get("completedLessons") != null) {
            enrollment.setCompletedLessons(updates.get("completedLessons").toString());
        }
        if (updates.containsKey("quizScore") && updates.get("quizScore") != null) {
            enrollment.setQuizScore(Integer.parseInt(updates.get("quizScore").toString()));
        }
        if (updates.containsKey("status") && updates.get("status") != null) {
            enrollment.setStatus(updates.get("status").toString());
        }
        if (updates.containsKey("certificateId") && updates.get("certificateId") != null) {
            enrollment.setCertificateId(updates.get("certificateId").toString());
        }
        if (updates.containsKey("completedAt") && updates.get("completedAt") != null) {
            enrollment.setCompletedAt(updates.get("completedAt").toString());
        }

        // Auto-generate certificate if completed and certificateId is absent
        if ("completed".equalsIgnoreCase(enrollment.getStatus()) &&
                (enrollment.getCertificateId() == null || enrollment.getCertificateId().isEmpty())) {
            String code = "CERT-2026-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            enrollment.setCertificateId(code);
            if (enrollment.getCompletedAt() == null || enrollment.getCompletedAt().isEmpty()) {
                enrollment.setCompletedAt(new Date().toString());
            }
        }

        CourseEnrollment saved = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable String id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

