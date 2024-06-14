package com.example.mydemoproject.controller;

import com.example.mydemoproject.dto.ErrorDto;
import com.example.mydemoproject.exceptions.ProductNotFoundException;
import com.example.mydemoproject.model.Category;
import com.example.mydemoproject.model.Product;
import com.example.mydemoproject.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController tells Spring that this class contains RESTful API endpoints.
@RestController
public class ProductController {
    // initializing productService here so that we can use Methods we have created in our Services
// Declaring productService as a final field to ensure it is initialized once and never changed.
    private final ProductService productService;


    //creating an instance of ProductController with constructor injecting
    // productService as DI and initializing it
    // Constructor-based dependency injection to initialize productService.
    public ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }






    // IMPLEMENTING CONTROLLER METHODS


    // @PostMapping is an annotation which tells Spring that the POST HTTP method should be
    // used for the following method

    //create a product
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // In the above line, ResponseEntity class is used with type of Product which calls a method createProduct
        // that takes in product of type Product from client using @RequestBody


        // Call the createProduct method of productService to create a new product and store the result in
        // postRequestResponse
        Product postRequestResponse = productService.createProduct(product);

        // if postRequestResponse is not null, it means a product has been successfully created.
        // Hence, we return a ResponseEntity object instance by passing in postRequestResponse and a "HTTP 201 Created"
        // status
        if (postRequestResponse != null) {
            return new ResponseEntity<>(
                    postRequestResponse, HttpStatus.CREATED);
        } else {
            //If postRequestResponse is null, it means product creation failed.
            // if not , we return a ResponseEntity object instance with Internal server error httpStatus.
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //@GetMapping is an annotation which tells Spring that the GET HTTP method should be used
    // for the following method

    // get a single product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        //In the above line, ResponseEntity class is used with the type Product which calls a method getProduct;
        // this method takes in variable which is "id" from the URL and assigns it to productId which is of Long type
        // Also, if the getProduct method fails to do its job which is getting product details from service layer,
        // ProductNotFoundException class is executed(explanation of its function
        // can be found in respective class)

        //In the below line, we are calling getSingleProduct method(of productService) by passing productId
        // that we are getting from the URL.
        // Then we are assigning the value to currentProduct(which is of Product type)
        Product currentProduct = productService.getSingleProduct(productId);

        // Now we are returning a new object Instance of ResponseEntity by passing the retrieved Product as
        // currentProduct with Http status of Ok
        return new ResponseEntity<>(
                currentProduct, HttpStatus.OK
        );
    }


    //Getting the list of Products
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam("pageSize") int pageSize,
                                                        @RequestParam("pageNumber") int pageNumber, @RequestParam("sortBy") String fieldName) throws ProductNotFoundException {
        Page<Product> products = productService.getAllProducts(pageSize, pageNumber, fieldName);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Updating a product
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product)
            throws ProductNotFoundException {
        Product putRequestResponse = productService.updateProduct(productId, product);

        return new ResponseEntity<>(putRequestResponse, HttpStatus.OK);
    }




    //Deleting a product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        try {
            Product deletedProduct = productService.deleteProduct(productId);
            return ResponseEntity.ok(deletedProduct);
        } catch(ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable("category") String category) {
        try {
            List<Product> products = productService.getProductsInCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e) {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(e.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }

}

