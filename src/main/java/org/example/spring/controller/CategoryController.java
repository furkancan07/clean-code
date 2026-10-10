package org.example.spring.controller;

import org.example.spring.locking.pesimistic.model.Category;
import org.example.spring.locking.pesimistic.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping({"/{id}"})
    public Category getCategoryById(@PathVariable Long id) {
        return service.findById(id);
    }
}
