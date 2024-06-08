package com.example.mydemoproject.service;

import com.example.mydemoproject.exceptions.ProductNotFoundException;
import com.example.mydemoproject.model.Category;
import com.example.mydemoproject.model.Product;
import com.example.mydemoproject.repositories.CategoryRepository;
import com.example.mydemoproject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        throw new ProductNotFoundException("Product not found");
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        Category cat = categoryRepository.findByTitle(product.getCategory().getTitle());
        if (cat == null) {
            Category newCat = new Category();
            newCat.setTitle(product.getCategory().getTitle());
            Category newRow = categoryRepository.save(newCat);
            product.setCategory(newRow);
        }else {
            product.setCategory(cat);
        }
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getProductsInCategory(String category) throws ProductNotFoundException {
        return List.of();
    }
}
