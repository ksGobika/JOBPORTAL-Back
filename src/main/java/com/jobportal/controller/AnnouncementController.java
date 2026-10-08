package com.jobportal.controller;

import com.jobportal.model.Announcement;
import com.jobportal.repository.AnnouncementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping
    public List<Announcement> getAnnouncements(@RequestParam(required = false) Boolean active) {
        if (active != null) {
            return announcementRepository.findByActive(active);
        }
        return announcementRepository.findAll();
    }

    @PostMapping
    public Announcement createAnnouncement(@RequestBody Announcement announcement) {
        if (announcement.getId() == null || announcement.getId().trim().isEmpty()) {
            announcement.setId(UUID.randomUUID().toString());
        }
        if (announcement.getDate() == null || announcement.getDate().trim().isEmpty()) {
            announcement.setDate(new Date().toString());
        }
        return announcementRepository.save(announcement);
    }
}
