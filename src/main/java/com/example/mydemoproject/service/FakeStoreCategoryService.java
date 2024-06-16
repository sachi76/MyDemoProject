package com.example.mydemoproject.service;

import com.example.mydemoproject.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreCategoryService implements CategoryService {

    private RestTemplate restTemplate;

    public FakeStoreCategoryService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Category getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
    String[] categoryNames = restTemplate.getForObject("https://fakestoreapi.com/products/categories",String[].class);

    List<Category> categories = new ArrayList<>();

        if(categoryNames != null){
            for(String name : categoryNames ){
                categories.add(new Category(name));
            }
        }

        return  categories;

    }

    @Override
    public List<Category> getCategoryByName(String categoryName) {
        return List.of();
    }

    @Override
    public Category saveCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public void deleteCategory(Category category) {

    }
}
