package com.example.mydemoproject.service;

import com.example.mydemoproject.dto.FakeStoreProductDto;
import com.example.mydemoproject.exceptions.ProductNotFoundException;
import com.example.mydemoproject.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements  ProductService{

    public RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
            return fakeStoreProductDto.toProduct();
        } else {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }


    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto.class
        );
        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
            return List.of(fakeStoreProductDto.toProduct());
        } else {
            throw new ProductNotFoundException("Products not found");
        }

    };

    @Override
    public Product updateProduct(Long productId, Product product){
        FakeStoreProductDto fs = new FakeStoreProductDto();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setPrice(2000);
        fs.setDescription(product.getDescription());
        fs.setCategory(product.getCategory().getTitle());
        fs.setImage(product.getImageUrl());

        restTemplate.put(
                "https://fakestoreapi.com/products/"+ productId,
                fs
        );
        return fs.toProduct();
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {

        String url = "https://fakestoreapi.com/products/" + productId;
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            throw new ProductNotFoundException("Product not found with id" + productId);
        }
    }

    @Override
    public Product createProduct(Product product)  {
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
