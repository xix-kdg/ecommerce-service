package com.kg.ecommerce.service;

import com.kg.ecommerce.fixtures.ProductTestData;
import com.kg.ecommerce.model.Product;
import com.kg.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // getProducts tests
    @Test
    void shouldReturnAllProducts() {
        // Given
        List<Product> mockProducts = ProductTestData.createProductList();
        when(productRepository.findAll()).thenReturn(mockProducts);

        // When
        List<Product> results = productService.getProducts();

        // Then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(3);
        assertThat(results).isEqualTo(mockProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() {
        // Given
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Product> results = productService.getProducts();

        // Then
        assertThat(results).isEmpty();
        verify(productRepository, times(1)).findAll();
    }

    // getProductById tests
    @Test
    void shouldReturnProductWhenIdExists() {
        // Given
        Product mockProduct = ProductTestData.createDefaultProduct();
        when(productRepository.findById(mockProduct.getId())).thenReturn(Optional.of(mockProduct));

        // When
        Product result = productService.getProductById(mockProduct.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockProduct);
        assertThat(result.getName()).isEqualTo(mockProduct.getName());
        verify(productRepository, times(1)).findById(mockProduct.getId());
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenProductIdNotFound() {
        // Given
        int nonExistentId = 999;
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.getProductById(nonExistentId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Product not found with id: " + nonExistentId);

        verify(productRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void shouldReturnAllProductsWhenSearchQueryIsEmpty() {
        // Given
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Product> results = productService.searchProducts("   ");

        // Then
        assertThat(results).isEmpty();
        verify(productRepository, times(1)).findAll();
        verify(productRepository, never()).searchProducts(anyString());
    }

    @Test
    void shouldCallSearchProductsRepositoryWhenQueryIsNotEmpty() {
        // Given
        String keyword = "Wireless";
        List<Product> mockProductList = ProductTestData.createProductList();
        when(productRepository.searchProducts(keyword)).thenReturn(mockProductList);

        // When
        List<Product> results = productService.searchProducts(keyword);

        // Then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(3);
        assertThat(results.getFirst().getName()).isEqualTo(mockProductList.getFirst().getName());

        // Verify that the service called the search query and NOT findAll()
        verify(productRepository, times(1)).searchProducts(keyword);
        verify(productRepository, never()).findAll();
    }
}
