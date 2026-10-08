package com.jobportal.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    private String id;

    private String employerId;
    private String companyName;
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String location;
    private String jobType;
    private String industry;
    private String experienceLevel;
    private String salaryRange;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String requiredSkills;

    private String deadline;
    private String postedAt;
    private String status;
    private Integer views = 0;

    public Job() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    @JsonRawValue
    public String getRequiredSkills() {
        return requiredSkills != null ? requiredSkills : "[]";
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViews() {
        return views != null ? views : 0;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
