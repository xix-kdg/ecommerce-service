package com.kg.ecommerce.controller;

import com.kg.ecommerce.fixtures.ProductBuilder;
import com.kg.ecommerce.fixtures.ProductTestData;
import com.kg.ecommerce.model.Product;
import com.kg.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static com.kg.ecommerce.constant.ApiRoutes.Products.BASE;
import static com.kg.ecommerce.constant.ApiRoutes.Products.PRODUCT;
import static com.kg.ecommerce.constant.ApiRoutes.Products.PRODUCTS;
import static com.kg.ecommerce.constant.ApiRoutes.Products.PRODUCTS_ID;
import static com.kg.ecommerce.constant.ApiRoutes.Products.SEARCH;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    // getProducts tests
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

    // getProducts/id tests
    @Test
    void shouldReturnProductByIdWithHttpOk() throws Exception {
        // Given
        Product product = ProductTestData.createDefaultProduct();
        when(productService.getProductById(product.getId())).thenReturn(product);

        // When & Then
        mockMvc.perform(get(BASE + PRODUCTS_ID, product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldReturnNotFoundWhenProductIdDoesNotExist() throws Exception {
        // Given
        int nonExistentId = 999;
        when(productService.getProductById(nonExistentId))
                .thenThrow(new ResponseStatusException(NOT_FOUND, "Product not found with id: " + nonExistentId));

        // When & Then
        mockMvc.perform(get(BASE + PRODUCTS_ID, nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // addProduct tests
    @Test
    void shouldCreateProductAndReturnCreatedStatus() throws Exception {
        // Given
        Product mockProduct = ProductBuilder.aProduct()
                .withId(11)
                .withName("New Product11")
                .build();

        when(productService.addProduct(any(Product.class))).thenReturn(mockProduct);

        // When & Then
        mockMvc.perform(post(BASE + PRODUCT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProduct)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(mockProduct.getId()))
                .andExpect(jsonPath("$.name").value(mockProduct.getName()));
    }

    @Test
    void shouldReturnInternalServerErrorWhenAddProductFails() throws Exception {
        // Given
        Product product = ProductTestData.createDefaultProduct();
        when(productService.addProduct(any(Product.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(post(BASE + PRODUCT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isInternalServerError());
    }

    // updatedProduct tests
    @Test
    void shouldUpdateProductAndReturnHttpOk() throws Exception {
        // Given
        Product product = ProductTestData.createDefaultProduct();
        when(productService.updateProduct(any(Product.class))).thenReturn(product);

        // When & Then
        mockMvc.perform(put(BASE + PRODUCT, product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    // deleteProduct tests
    @Test
    void shouldDeleteProductAndReturnOk() throws Exception {
        // Given
        int productId = 1;
        doNothing().when(productService).deleteProduct(productId);

        // When & Then
        mockMvc.perform(delete(BASE + PRODUCTS_ID, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(productId);
    }

    // searchProducts tests
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
