package com.jobportal.controller;

import com.jobportal.model.Category;
import com.jobportal.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        if (category.getId() == null || category.getId().trim().isEmpty()) {
            category.setId(UUID.randomUUID().toString());
        }
        return categoryRepository.save(category);
    }
}
