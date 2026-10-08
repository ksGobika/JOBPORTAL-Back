package com.jobportal.repository;

import com.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, String> {
    List<Job> findByStatus(String status);
    List<Job> findByEmployerId(String employerId);
    List<Job> findByEmployerIdAndStatus(String employerId, String status);
}
