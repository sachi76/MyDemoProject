package com.example.mydemoproject.controller;

import com.example.mydemoproject.model.Product;
import com.example.mydemoproject.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductService productService;

    public  ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product ){

        Product postRequestResponse = productService.createProduct(product);
        return postRequestResponse;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        Product currentProduct = productService.getSingleProduct(productId);
        return currentProduct;
    }

    @GetMapping("/products")
    public void getAllProducts(){
        productService.getAllProducts();
    }

}
