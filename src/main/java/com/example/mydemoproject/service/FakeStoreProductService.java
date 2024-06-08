package com.example.mydemoproject.service;

import com.example.mydemoproject.dto.FakeStoreProductDto;
import com.example.mydemoproject.exceptions.ProductNotFoundException;
import com.example.mydemoproject.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("fakeStoreProductService")
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
        List<Product> products = new ArrayList<>();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
            for(FakeStoreProductDto fs: responseEntity.getBody()){
                products.add(fs.toProduct());
            }
        } else {
            throw new ProductNotFoundException("Products not found");
        }

        return products;
    };

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException{
        FakeStoreProductDto fs = new FakeStoreProductDto();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setPrice(2000);
        fs.setDescription(product.getDescription());
        fs.setCategory(product.getCategory().getTitle());
        fs.setImage(product.getImageUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fs, headers);

        try {
            ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                    "https://fakestoreapi.com/products/" + productId,
                    HttpMethod.PUT,
                    requestEntity,
                    FakeStoreProductDto.class
            );
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
                return responseEntity.getBody().toProduct();
            }else{
                throw new ProductNotFoundException("Product not found with id " + productId);
            }
        } catch (HttpClientErrorException.NotFound e){
            throw new ProductNotFoundException("Product not found with id " + productId);
        } catch (RestClientException e){
            throw new RuntimeException("Failed to update product with id: " + productId, e);
        }

    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {

        String url = "https://fakestoreapi.com/products/" + productId;

        // Fetch the product details
        ResponseEntity<FakeStoreProductDto> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, FakeStoreProductDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }

        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }

        FakeStoreProductDto dto = responseEntity.getBody();

        // Delete the product
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            throw new RuntimeException("Error interacting with the FakeStore API", e);
        }

        return dto.toEmptyProduct();

    }

    @Override
    public List<Product> getProductsInCategory(String category) throws ProductNotFoundException  {
        String url = "https://fakestoreapi.com/products/category/" + category;

        ResponseEntity<FakeStoreProductDto[]> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, FakeStoreProductDto[].class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("Products not found in category " + category);
        }

        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            throw new ProductNotFoundException("Products not found in category " + category);
        }
        FakeStoreProductDto[] dtos = responseEntity.getBody();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto dto : dtos) {
            products.add(dto.toEmptyProduct());
        }

        return products;

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

        //Prepare the Http request entity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fs, headers);

        try{
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products",
                HttpMethod.POST,
                requestEntity,
                FakeStoreProductDto.class
        );

        //Check the response status and body
            if (responseEntity.getStatusCode() == HttpStatus.CREATED && responseEntity.getBody() != null) {
                return responseEntity.getBody().toProduct();
            }
        }catch (RestClientException e){
            throw new RuntimeException("Failed to create a product", e);
        }
        return product;
    }

}
