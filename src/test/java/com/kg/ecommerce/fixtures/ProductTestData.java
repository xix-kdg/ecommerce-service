package com.kg.ecommerce.fixtures;

import com.kg.ecommerce.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductTestData {

    public static Product createDefaultProduct() {
        return new Product("AeroStream Wireless", 1, "Headphones", "Sonic", "Electronics",
                "img.jpg", new BigDecimal("100"), 10, LocalDateTime.now(), true);
    }

    public static List<Product> createProductList() {
        return List.of(
                new Product("AeroStream Wireless", 1, "Headphones", "Sonic", "Electronics", "img1.jpg", new BigDecimal("149.99"), 12, LocalDateTime.now(), true),
                new Product("ErgoBack Mesh", 2, "Office Chair", "SitWell", "Furniture", "img2.jpg", new BigDecimal("249.50"), 4, LocalDateTime.now(), true),
                new Product("Apex Runner X", 3, "Running Shoes", "Strider", "Apparel", "img3.jpg", new BigDecimal("120.00"), 24, LocalDateTime.now(), true)
        );
    }
}
