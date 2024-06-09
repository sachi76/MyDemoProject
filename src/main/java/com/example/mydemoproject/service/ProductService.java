package com.example.mydemoproject.service;

import com.example.mydemoproject.exceptions.ProductNotFoundException;
import com.example.mydemoproject.model.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product) throws ProductNotFoundException;
    Product deleteProduct(Long productId) throws ProductNotFoundException;

    List<Product> getProductsInCategory(String category) throws ProductNotFoundException    ;

}
