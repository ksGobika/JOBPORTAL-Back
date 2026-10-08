package com.jobportal.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String profile;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String company;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String savedJobs;

    public User() {}

    public User(String id, String name, String email, String password, String role, String profile, String company, String savedJobs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile = profile;
        this.company = company;
        this.savedJobs = savedJobs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonRawValue
    public String getProfile() {
        return profile != null ? profile : "null";
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @JsonRawValue
    public String getCompany() {
        return company != null ? company : "null";
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @JsonRawValue
    public String getSavedJobs() {
        return savedJobs != null ? savedJobs : "[]";
    }

    public void setSavedJobs(String savedJobs) {
        this.savedJobs = savedJobs;
    }
}
