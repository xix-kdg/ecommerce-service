package com.kg.ecommerce.service;

import com.kg.ecommerce.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    List<Product> mockProducts = new ArrayList<>(
            List.of(
                    new Product(
                            "QuantumX Wireless Headphones",
                            1,
                            "Active noise-canceling over-ear headphones with 40-hour battery life.",
                            "AudioPhile",
                            "cat-elec-01",
                            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e",
                            4.7,
                            1250,
                            LocalDateTime.of(2026, 1, 15, 8, 30),
                            true
                    ),
                    new Product(
                            "TitanCharge 10K Power Bank",
                            2,
                            "Compact 10,000mAh fast-charging portable power bank with USB-C PD.",
                            "VoltTech",
                            "cat-elec-02",
                            "https://images.unsplash.com/photo-1609592424109-dd9892f1b177",
                            4.3,
                            412,
                            LocalDateTime.of(2026, 2, 20, 11, 15),
                            true
                    ),
                    new Product(
                            "ApexPro Mechanical Keyboard",
                            3,
                            "RGB backlit mechanical keyboard with hot-swappable linear switches.",
                            "ClickGamer",
                            "cat-elec-03",
                            "https://images.unsplash.com/photo-1587829741301-dc798b83add3",
                            4.8,
                            890,
                            LocalDateTime.of(2026, 3, 5, 14, 22),
                            true
                    ),
                    new Product(
                            "ChronoFit Smartwatch v2",
                            4,
                            "Fitness tracker with AMOLED display, heart rate monitor, and built-in GPS.",
                            "AuraWear",
                            "cat-elec-04",
                            "https://images.unsplash.com/photo-1523275335684-37898b6baf30",
                            4.2,
                            230,
                            LocalDateTime.of(2026, 4, 12, 9, 45),
                            false
                    ),
                    new Product(
                            "StreamView 4K Webcam",
                            5,
                            "4K Ultra HD webcam with dual microphones and automatic light correction.",
                            "VisionTech",
                            "cat-elec-03",
                            "https://images.unsplash.com/photo-1600541519463-f90af88d0b51",
                            4.5,
                            78,
                            LocalDateTime.of(2026, 5, 18, 16, 10),
                            true
                    )
            )
    );

    public List<Product> getProducts() {
        return mockProducts;
    }

    public Product getProductById(int id) {
        return mockProducts.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
    }

    public void addProduct(Product product) {
        mockProducts.add(product);
    }

    public void updateProduct(Product product) {
        int index = getIndexById(product.getId());

        if (index >= 0) {
            mockProducts.set(index, product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + product.getId());
        }
    }

    public void deleteProduct(int id) {
        boolean removed = mockProducts.removeIf(p -> p.getId() == id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
    }

    private int getIndexById(int id) {
        int index = -1;
        for (int i = 0; i < mockProducts.size(); i++) {
            if (mockProducts.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }
}
