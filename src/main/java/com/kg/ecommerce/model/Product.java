package com.kg.ecommerce.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Product {
    private int id;
    private String name;
    private String description;
    private String brand;
    private String categoryId;

    private String imageUrl;
    private double averageRating;
    private int reviewCount;

    private LocalDateTime createdAt;
    private boolean isAvailable;

    public Product() {
    }

    public Product(
            String name,
            int id,
            String description,
            String brand,
            String categoryId,
            String imageUrl,
            double averageRating,
            int reviewCount,
            LocalDateTime createdAt,
            boolean isAvailable
    ) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", averageRating=" + averageRating +
                ", reviewCount=" + reviewCount +
                ", createdAt=" + createdAt +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
