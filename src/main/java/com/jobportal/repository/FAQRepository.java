package com.jobportal.repository;

import com.jobportal.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, String> {
}
