package com.shop.shop.Dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shop.shop.Model.CarModel;



public class GetCarsOfUserDto {
    public Long id;
    public boolean status;
    public BigDecimal totalAmount;
    public BigDecimal grandTotal;
    public LocalDateTime carDate;
    public List <GetProductsOfCarDto> ProductsOfCarDto;
    public BigDecimal shippingCost;
    public GetCartLocationDto cartLocation;

    public GetCarsOfUserDto(){}

    public GetCarsOfUserDto(Long id, LocalDateTime carDate, boolean status, BigDecimal totalAmount, BigDecimal grandTotal, BigDecimal shippingCost, List<GetProductsOfCarDto> ProductsOfCarDto, GetCartLocationDto cartLocation) {
        this.id = id;
        this.carDate = carDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.grandTotal = grandTotal;
        this.shippingCost = shippingCost;
        this.ProductsOfCarDto = ProductsOfCarDto;
        this.cartLocation = cartLocation;
    }

    public static GetCarsOfUserDto convertCarsToDto(CarModel cart, List <GetProductsOfCarDto> productsOfCarList){
        
        GetCartLocationDto cartLocation = new GetCartLocationDto(
            cart.getCountry(),
            cart.getState(),
            cart.getZip(),
            cart.getAddress()
        );

        GetCarsOfUserDto carsOfUserDto = new GetCarsOfUserDto(
            cart.getId(),
            cart.getCarDate(),
            cart.isStatus(),
            cart.getTotalAmount(),
            cart.getGrandTotal(),
            cart.getShippingCost(),
            productsOfCarList,
            cartLocation
        );
        return carsOfUserDto;
    }

}