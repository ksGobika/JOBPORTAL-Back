package com.jobportal.controller;

import com.jobportal.model.Course;
import com.jobportal.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses(
            @RequestParam(required = false) String employerId,
            @RequestParam(required = false) String category
    ) {
        if (employerId != null && !employerId.trim().isEmpty()) {
            return courseRepository.findByEmployerId(employerId);
        }
        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All")) {
            return courseRepository.findByCategory(category);
        }
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        return courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        if (course.getId() == null || course.getId().trim().isEmpty()) {
            course.setId("course-" + UUID.randomUUID().toString().substring(0, 8));
        }
        if (course.getCreatedAt() == null || course.getCreatedAt().trim().isEmpty()) {
            course.setCreatedAt(new Date().toString());
        }
        if (course.getEnrolledCount() == null) {
            course.setEnrolledCount(0);
        }
        if (course.getStatus() == null || course.getStatus().trim().isEmpty()) {
            course.setStatus("published");
        }
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String id, @RequestBody Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existing -> {
                    if (updatedCourse.getTitle() != null) existing.setTitle(updatedCourse.getTitle());
                    if (updatedCourse.getDescription() != null) existing.setDescription(updatedCourse.getDescription());
                    if (updatedCourse.getCategory() != null) existing.setCategory(updatedCourse.getCategory());
                    if (updatedCourse.getLevel() != null) existing.setLevel(updatedCourse.getLevel());
                    if (updatedCourse.getPlatform() != null) existing.setPlatform(updatedCourse.getPlatform());
                    if (updatedCourse.getCourseUrl() != null) existing.setCourseUrl(updatedCourse.getCourseUrl());
                    if (updatedCourse.getDuration() != null) existing.setDuration(updatedCourse.getDuration());
                    if (updatedCourse.getSyllabus() != null) existing.setSyllabus(updatedCourse.getSyllabus());
                    if (updatedCourse.getQuizData() != null) existing.setQuizData(updatedCourse.getQuizData());
                    if (updatedCourse.getThumbnail() != null) existing.setThumbnail(updatedCourse.getThumbnail());
                    if (updatedCourse.getEnrolledCount() != null) existing.setEnrolledCount(updatedCourse.getEnrolledCount());
                    return ResponseEntity.ok(courseRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

