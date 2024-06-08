package com.example.mydemoproject.repositories;

import com.example.mydemoproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Product findByTitle(String title);
    Product findByDescription(String description);
    Product findById(long id);



}
