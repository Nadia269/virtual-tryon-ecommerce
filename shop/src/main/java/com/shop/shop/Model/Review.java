package com.shop.shop.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.util.Objects;

@Entity
public class Review {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

   

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;


    public Review() {
    }

    public Review(Long id, int rating,  LocalDateTime createdAt, UserModel user, ProductModel product) {
        this.id = id;
        this.rating = rating;
       
        this.createdAt = createdAt;
        this.user = user;
        this.product = product;
    }

    public Review id(Long id) {
        setId(id);
        return this;
    }

    public Review rating(int rating) {
        setRating(rating);
        return this;
    }

    public Review createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public Review user(UserModel user) {
        setUser(user);
        return this;
    }

    public Review product(ProductModel product) {
        setProduct(product);
        return this;
    }

    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ProductModel getProduct() {
        return this.product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}