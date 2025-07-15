package com.shop.shop.Model;

import java.math.BigDecimal;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name="car")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private BigDecimal totalAmount;
    private BigDecimal grandTotal;
    private LocalDateTime carDate;
    private BigDecimal shippingCost;
    private String country;
    private String state;
    private String zip;
    private String address;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserModel userModel;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarProductModel> carProducts = new ArrayList<>();

    public CarModel(){

    }

    public CarModel(Long id, LocalDateTime carDate, boolean status, BigDecimal totalAmount, BigDecimal grandTotal, BigDecimal shippingCost, String country, String state, String zip, String address) {
        this.id = id;
        this.carDate = carDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.grandTotal = grandTotal;
        this.shippingCost = shippingCost;
        this.country = country;
        this.state = state;
        this.zip = zip;
        this.address = address;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCarDate() {
        return carDate;
    }

    public void setCarDate(LocalDateTime carDate) {
        this.carDate = carDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<CarProductModel> getCarProducts() {
        return carProducts;
    }

    public void setCarProducts(List<CarProductModel> carProducts) {
        this.carProducts = carProducts;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
