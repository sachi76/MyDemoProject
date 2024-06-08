package com.example.mydemoproject.repositories;

import com.example.mydemoproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
