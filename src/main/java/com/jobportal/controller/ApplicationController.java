package com.jobportal.controller;

import com.jobportal.model.Application;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    public ApplicationController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public List<Application> getApplications(
            @RequestParam(required = false) String seekerId,
            @RequestParam(required = false) String employerId,
            @RequestParam(required = false) String jobId
    ) {
        if (jobId != null && seekerId != null) {
            return applicationRepository.findByJobIdAndSeekerId(jobId, seekerId);
        } else if (seekerId != null) {
            return applicationRepository.findBySeekerId(seekerId);
        } else if (employerId != null) {
            return applicationRepository.findByEmployerId(employerId);
        } else if (jobId != null) {
            return applicationRepository.findByJobId(jobId);
        }
        return applicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable String id) {
        return applicationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody Application application) {
        if (application.getSeekerId() == null || application.getSeekerId().trim().isEmpty() || application.getSeekerId().startsWith("seeker-")) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Valid authenticated seekerId is required to apply for a job."));
        }
        if (application.getJobId() == null || application.getJobId().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Valid jobId is required."));
        }
        if (application.getId() == null || application.getId().trim().isEmpty()) {
            application.setId(UUID.randomUUID().toString());
        }
        if (application.getAppliedAt() == null || application.getAppliedAt().trim().isEmpty()) {
            application.setAppliedAt(new Date().toString());
        }
        if (application.getStatus() == null || application.getStatus().trim().isEmpty()) {
            application.setStatus("applied");
        }
        return ResponseEntity.ok(applicationRepository.save(application));
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Optional<Application> appOpt = applicationRepository.findById(id);
        if (appOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Application application = appOpt.get();
        if (updates.containsKey("status") && updates.get("status") != null) {
            application.setStatus(updates.get("status").toString());
        }

        return ResponseEntity.ok(applicationRepository.save(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
