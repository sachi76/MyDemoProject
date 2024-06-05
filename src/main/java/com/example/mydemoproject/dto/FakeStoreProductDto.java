package com.example.mydemoproject.dto;

import com.example.mydemoproject.model.Category;
import com.example.mydemoproject.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public Product toProduct(){
        Product p = new Product();
        p.setId(id);
        p.setTitle(title);
        p.setPrice(price);
        p.setDescription(description);
        p.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);

        p.setCategory(cat);

        return p;

    }

    @Override
    public String toString(){
        return "FakeStoreProductDto [id="
                + id + ", title=" + title + ", price=" + price + ", category="
                + category + ", description=" + description + ", image=" + image
                + '\'' + "]";
    }

    public Product toEmptyProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(""); // Clear the field
        product.setPrice(0.0); // Clear the field
//        product.setCategory(""); // Clear the field
        product.setDescription(""); // Clear the field
        product.setImageUrl(""); // Clear the field
        return product;
    }
}
