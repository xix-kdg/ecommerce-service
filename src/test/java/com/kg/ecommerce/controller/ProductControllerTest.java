package com.kg.ecommerce.controller;

import com.kg.ecommerce.fixtures.ProductTestData;
import com.kg.ecommerce.model.Product;
import com.kg.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.kg.ecommerce.constant.ApiRoutes.Products.BASE;
import static com.kg.ecommerce.constant.ApiRoutes.Products.PRODUCTS;
import static com.kg.ecommerce.constant.ApiRoutes.Products.SEARCH;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldReturnAllProductsWithHttpOk() throws Exception {
        // Given
        List<Product> products = ProductTestData.createProductList();
        when(productService.getProducts()).thenReturn(products);

        // When & Then
        mockMvc.perform(get(BASE + PRODUCTS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(products.getFirst().getId()));
    }

    @Test
    void shouldReturnEmptyProductList() throws Exception {
        // Given
        when(productService.getProducts()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get(BASE + PRODUCTS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnMultipleProducts() throws Exception {
        // Given
        List<Product> products = ProductTestData.createProductList();
        when(productService.getProducts()).thenReturn(products);

        // When & Then
        mockMvc.perform(get(BASE + PRODUCTS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(products.size())));
    }

    // TODO: getProducts Exception test

    @Test
    void shouldReturnHttpOkAndProductListOnSearch() throws Exception {
        // Given
        Product product = ProductTestData.createDefaultProduct();
        when(productService.searchProducts(product.getName())).thenReturn(List.of(product));

        // When & Then
        mockMvc.perform(get(BASE + SEARCH)
                        .param(ProductController.SEARCH_REQUEST_PARAM, product.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
