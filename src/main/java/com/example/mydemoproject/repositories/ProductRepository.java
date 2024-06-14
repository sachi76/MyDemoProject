package com.example.mydemoproject.repositories;

import com.example.mydemoproject.model.Product;
import com.example.mydemoproject.repositories.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //JPA implementation
    Product save(Product product);
    Product findByTitle(String title);
    Product findByDescription(String description);
    Product findById(long id);
    Page<Product> findAll(Pageable pageable);



    //How to implement using HQL

    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> getProductsByCategoryId(@Param("categoryId") Long categoryId);

    //How to implement using Native query language
    @Query(value = "select * from product p where p.category_id  = :categoryId", nativeQuery = true)
    List<Product> getProductsByCategoryIdWithNativeQueries(@Param("categoryId") Long categoryId);

    //HQL with Projections
    //Allows us to fetch specific columns from the db
    @Query(value = "select p.title as title, p.id as id from Product p where p.category.id = :categoryId")
    List<ProductProjection> getProductsByCatIdwithProjections(@Param("categoryId") Long categoryId);



}
