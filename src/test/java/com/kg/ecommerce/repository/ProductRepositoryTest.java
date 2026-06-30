package com.kg.ecommerce.repository;

import com.kg.ecommerce.fixtures.ProductTestData;
import com.kg.ecommerce.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    // CRUD Operations Tests
    @Test
    void shouldSaveProductAndAssignId() {
        // Given
        Product product = ProductTestData.createNewProduct();

        // When
        Product savedProduct = productRepository.save(product);

        // Then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }

    @Test
    void shouldFindProductById() {
        // Given
        Product product = ProductTestData.createNewProduct();
        Product savedProduct = productRepository.save(product);

        // When
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo(product.getName());
    }

    @Test
    void shouldReturnEmptyWhenProductIdNotFound() {
        // When
        Optional<Product> foundProduct = productRepository.findById(999);

        // Then
        assertThat(foundProduct).isEmpty();
    }

    @Test
    void shouldReturnAllProducts() {
        // Given
        productRepository.saveAll(ProductTestData.createNewProductList());

        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertThat(products).hasSize(3);
    }

    @Test
    void shouldUpdateProduct() {
        // Given
        Product product = ProductTestData.createNewProduct();
        Product savedProduct = productRepository.save(product);

        // When
        savedProduct.setName("Updated Name");
        savedProduct.setPrice(new BigDecimal("60.00"));
        productRepository.save(savedProduct);

        // Then
        Product updatedProduct = productRepository.findById(savedProduct.getId()).orElseThrow();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Name");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal("60.00"));
    }

    @Test
    void shouldDeleteProduct() {
        // Given
        Product product = ProductTestData.createNewProduct();
        Product savedProduct = productRepository.save(product);

        // When
        productRepository.delete(savedProduct);

        // Then
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertThat(foundProduct).isEmpty();
    }

    // Search Products Tests
    @Test
    void shouldSearchProductsByName() {
        // Given
        Product product = ProductTestData.createNewProduct();
        productRepository.save(product);

        // When
        List<Product> results = productRepository.searchProducts("AeroStream");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getName()).isEqualTo(product.getName());
    }

    @Test
    void shouldSearchProductsByBrand() {
        // Given
        Product product = ProductTestData.createNewProduct();
        productRepository.save(product);

        // When
        List<Product> results = productRepository.searchProducts("Sonic");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getBrand()).isEqualTo(product.getBrand());
    }

    @Test
    void shouldSearchProductsByCategory() {
        // Given
        Product product = ProductTestData.createNewProduct();
        productRepository.save(product);

        // When
        List<Product> results = productRepository.searchProducts("Electronics");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getCategory()).isEqualTo("Electronics");
    }

    @Test
    void shouldSearchProductsByDescription() {
        // Given
        Product product = ProductTestData.createNewProduct();
        productRepository.save(product);

        // When
        List<Product> results = productRepository.searchProducts("Headphones");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getDescription()).contains(product.getDescription());
    }
}
