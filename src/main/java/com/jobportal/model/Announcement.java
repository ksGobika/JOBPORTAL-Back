package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    private String id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String message;

    private String date;
    private Boolean active = true;

    public Announcement() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
