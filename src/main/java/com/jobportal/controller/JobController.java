package com.jobportal.controller;

import com.jobportal.model.Job;
import com.jobportal.repository.JobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping
    public List<Job> getJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String employerId
    ) {
        if (employerId != null && status != null) {
            return jobRepository.findByEmployerIdAndStatus(employerId, status);
        } else if (employerId != null) {
            return jobRepository.findByEmployerId(employerId);
        } else if (status != null) {
            return jobRepository.findByStatus(status);
        }
        return jobRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id) {
        return jobRepository.findById(id)
                .map(job -> {
                    // Increment views if needed
                    job.setViews(job.getViews() + 1);
                    jobRepository.save(job);
                    return ResponseEntity.ok(job);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Job createJob(@RequestBody Map<String, Object> jobData) {
        Job job = new Job();
        job.setId(jobData.containsKey("id") && jobData.get("id") != null ? jobData.get("id").toString() : UUID.randomUUID().toString());
        job.setEmployerId(jobData.containsKey("employerId") && jobData.get("employerId") != null ? jobData.get("employerId").toString() : "");
        job.setCompanyName(jobData.containsKey("companyName") && jobData.get("companyName") != null ? jobData.get("companyName").toString() : "");
        job.setTitle(jobData.containsKey("title") && jobData.get("title") != null ? jobData.get("title").toString() : "");
        job.setDescription(jobData.containsKey("description") && jobData.get("description") != null ? jobData.get("description").toString() : "");
        job.setLocation(jobData.containsKey("location") && jobData.get("location") != null ? jobData.get("location").toString() : "");
        job.setJobType(jobData.containsKey("jobType") && jobData.get("jobType") != null ? jobData.get("jobType").toString() : "");
        job.setIndustry(jobData.containsKey("industry") && jobData.get("industry") != null ? jobData.get("industry").toString() : "");
        job.setExperienceLevel(jobData.containsKey("experienceLevel") && jobData.get("experienceLevel") != null ? jobData.get("experienceLevel").toString() : "");
        job.setSalaryRange(jobData.containsKey("salaryRange") && jobData.get("salaryRange") != null ? jobData.get("salaryRange").toString() : "");
        if (jobData.containsKey("requiredSkills") && jobData.get("requiredSkills") != null) {
            job.setRequiredSkills(toJsonString(jobData.get("requiredSkills")));
        }
        job.setDeadline(jobData.containsKey("deadline") && jobData.get("deadline") != null ? jobData.get("deadline").toString() : "");
        job.setPostedAt(jobData.containsKey("postedAt") && jobData.get("postedAt") != null ? jobData.get("postedAt").toString() : new Date().toString());
        job.setStatus(jobData.containsKey("status") && jobData.get("status") != null ? jobData.get("status").toString() : "pending");
        job.setViews(jobData.containsKey("views") && jobData.get("views") != null ? Integer.parseInt(jobData.get("views").toString()) : 0);

        return jobRepository.save(job);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<Job> updateJob(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Optional<Job> jobOpt = jobRepository.findById(id);
        if (jobOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Job job = jobOpt.get();
        if (updates.containsKey("status") && updates.get("status") != null) {
            job.setStatus(updates.get("status").toString());
        }
        if (updates.containsKey("title") && updates.get("title") != null) {
            job.setTitle(updates.get("title").toString());
        }
        if (updates.containsKey("description") && updates.get("description") != null) {
            job.setDescription(updates.get("description").toString());
        }
        if (updates.containsKey("location") && updates.get("location") != null) {
            job.setLocation(updates.get("location").toString());
        }
        if (updates.containsKey("jobType") && updates.get("jobType") != null) {
            job.setJobType(updates.get("jobType").toString());
        }
        if (updates.containsKey("industry") && updates.get("industry") != null) {
            job.setIndustry(updates.get("industry").toString());
        }
        if (updates.containsKey("experienceLevel") && updates.get("experienceLevel") != null) {
            job.setExperienceLevel(updates.get("experienceLevel").toString());
        }
        if (updates.containsKey("companyName") && updates.get("companyName") != null) {
            job.setCompanyName(updates.get("companyName").toString());
        }
        if (updates.containsKey("deadline") && updates.get("deadline") != null) {
            job.setDeadline(updates.get("deadline").toString());
        }
        if (updates.containsKey("salaryRange") && updates.get("salaryRange") != null) {
            job.setSalaryRange(updates.get("salaryRange").toString());
        }
        if (updates.containsKey("requiredSkills") && updates.get("requiredSkills") != null) {
            job.setRequiredSkills(toJsonString(updates.get("requiredSkills")));
        }
        if (updates.containsKey("views") && updates.get("views") != null) {
            job.setViews(Integer.parseInt(updates.get("views").toString()));
        }

        return ResponseEntity.ok(jobRepository.save(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private String toJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}
