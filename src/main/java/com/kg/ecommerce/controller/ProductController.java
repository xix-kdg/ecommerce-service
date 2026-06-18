package com.kg.ecommerce.controller;

import com.kg.ecommerce.model.Product;
import com.kg.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/api/products/{id}")
    public Product getProductsById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @PostMapping("/api/product")
    public void addProduct(@RequestBody Product product) {
        service.addProduct(product);
    }

    @PutMapping("/api/product")
    public void updateProduct(@RequestBody Product product) {
        service.updateProduct(product);
    }

    @DeleteMapping("/api/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }
}
