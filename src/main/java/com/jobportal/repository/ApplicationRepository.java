package com.jobportal.repository;

import com.jobportal.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {
    List<Application> findBySeekerId(String seekerId);
    List<Application> findByEmployerId(String employerId);
    List<Application> findByJobId(String jobId);
    List<Application> findByJobIdAndSeekerId(String jobId, String seekerId);
}
