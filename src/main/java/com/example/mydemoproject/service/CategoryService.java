package com.example.mydemoproject.service;

import com.example.mydemoproject.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> getCategoryByName(String categoryName);
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Category category);


}
