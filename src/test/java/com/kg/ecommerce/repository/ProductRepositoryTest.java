package com.kg.ecommerce.repository;

import com.kg.ecommerce.fixtures.ProductTestData;
import com.kg.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindProductsByMatchingCategoryIgnoreCase() {
        // Given
        Product mockProduct = ProductTestData.createDefaultProduct();
        productRepository.save(mockProduct);

        // When
        List<Product> result = productRepository.searchProducts("electronics");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getName()).isEqualTo(mockProduct.getName());
    }
}
