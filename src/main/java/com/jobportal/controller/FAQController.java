package com.jobportal.controller;

import com.jobportal.model.FAQ;
import com.jobportal.repository.FAQRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/faqs")
public class FAQController {

    private final FAQRepository faqRepository;

    public FAQController(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @GetMapping
    public List<FAQ> getFAQs() {
        return faqRepository.findAll();
    }

    @PostMapping
    public FAQ createFAQ(@RequestBody FAQ faq) {
        if (faq.getId() == null || faq.getId().trim().isEmpty()) {
            faq.setId(UUID.randomUUID().toString());
        }
        return faqRepository.save(faq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable String id) {
        if (faqRepository.existsById(id)) {
            faqRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
