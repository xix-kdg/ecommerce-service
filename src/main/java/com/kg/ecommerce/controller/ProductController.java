package com.kg.ecommerce.controller;

import com.kg.ecommerce.constant.ApiRoutes;
import com.kg.ecommerce.model.Product;
import com.kg.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiRoutes.Products.BASE)
public class ProductController {

    final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public static final String SEARCH_REQUEST_PARAM = "keyword";
    public static final String PATH_ID = "/{id}";

    @GetMapping(ApiRoutes.Products.PRODUCTS)
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(service.getProducts());
    }

    @GetMapping(ApiRoutes.Products.PRODUCTS_ID)
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping(ApiRoutes.Products.PRODUCT)
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = service.addProduct(product);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path(PATH_ID)
                    .buildAndExpand(addedProduct.getId())
                    .toUri();
            return ResponseEntity.created(location).body(addedProduct);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(ApiRoutes.Products.PRODUCT)
    public void updateProduct(@RequestBody Product product) {
        service.updateProduct(product);
    }

    @DeleteMapping(ApiRoutes.Products.PRODUCTS_ID)
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }

    @GetMapping(ApiRoutes.Products.SEARCH)
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(SEARCH_REQUEST_PARAM) String keyword) {
        return ResponseEntity.ok(service.searchProducts(keyword));
    }
}
