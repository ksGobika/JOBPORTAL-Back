package com.jobportal.controller;

import com.jobportal.model.Notification;
import com.jobportal.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public List<Notification> getNotifications(@RequestParam(required = false) String userId) {
        if (userId != null && !userId.trim().isEmpty()) {
            return notificationRepository.findByUserId(userId);
        }
        return notificationRepository.findAll();
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        if (notification.getId() == null || notification.getId().trim().isEmpty()) {
            notification.setId(UUID.randomUUID().toString());
        }
        if (notification.getCreatedAt() == null || notification.getCreatedAt().trim().isEmpty()) {
            notification.setCreatedAt(new Date().toString());
        }
        return notificationRepository.save(notification);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Notification> updateNotification(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Optional<Notification> notifOpt = notificationRepository.findById(id);
        if (notifOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = notifOpt.get();
        if (updates.containsKey("isRead") && updates.get("isRead") != null) {
            notification.setIsRead(Boolean.parseBoolean(updates.get("isRead").toString()));
        }

        return ResponseEntity.ok(notificationRepository.save(notification));
    }
}
