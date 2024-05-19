package com.example.mydemoproject.service;

import com.example.mydemoproject.dto.FakeStoreProductDto;
import com.example.mydemoproject.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements  ProductService{

    public RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fs = new FakeStoreProductDto();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setCategory(product.getCategory().getTitle());
        fs.setImage(product.getImageUrl());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());

        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fs,
                FakeStoreProductDto.class
        );
        return response.toProduct();
    }
}
