package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_enrollments")
public class CourseEnrollment {

    @Id
    private String id;

    private String courseId;
    private String courseTitle;
    private String seekerId;
    private String seekerName;
    private String seekerEmail;
    private String companyName;
    private String employerId;

    private Integer progress = 0; // 0 to 100

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String completedLessons;

    private String status = "enrolled"; // "enrolled" or "completed"
    private Integer quizScore = 0; // percentage
    private String certificateId;
    private String enrolledAt;
    private String completedAt;

    public CourseEnrollment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(String seekerId) {
        this.seekerId = seekerId;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getSeekerEmail() {
        return seekerEmail;
    }

    public void setSeekerEmail(String seekerEmail) {
        this.seekerEmail = seekerEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(String completedLessons) {
        this.completedLessons = completedLessons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(String enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}

