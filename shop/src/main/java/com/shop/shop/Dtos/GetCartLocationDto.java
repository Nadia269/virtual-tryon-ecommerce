package com.shop.shop.Dtos;


public class GetCartLocationDto {
    public String country;
    public String state;
    public String zip;
    public String address;

    public GetCartLocationDto(String country, String state, String zip, String address){
        this.country = country;
        this.state = state;
        this.zip = zip;
        this.address = address;
    }

    
}
