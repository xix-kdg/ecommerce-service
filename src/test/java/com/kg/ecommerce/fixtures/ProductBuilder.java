package com.kg.ecommerce.fixtures;

import com.kg.ecommerce.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductBuilder {

    private int id = 1;
    private String name = "Default Product";
    private String description = "Default Description";
    private String brand = "Default Brand";
    private String category = "Electronics";
    private String imageUrl = "img.jpg";
    private BigDecimal price = new BigDecimal("100.00");
    private Integer quantity = 10;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isAvailable = true;

    // Static factory method to kick off the chain
    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductBuilder outOfStock() {
        this.quantity = 0;
        this.isAvailable = false;
        return this;
    }

    // Final step to compile the product object
    public Product build() {
        return new Product(name, id, description, brand, category, imageUrl, price, quantity, createdAt, isAvailable);
    }
}
