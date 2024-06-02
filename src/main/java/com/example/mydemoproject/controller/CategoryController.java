package com.example.mydemoproject.controller;

import com.example.mydemoproject.service.CategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }




}
