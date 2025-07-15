package com.shop.shop.Dtos;

import com.shop.shop.Model.UserModel;

public class UserDTO {
    private String fullname;
    private String username;
    private String phone;
    private String address;
    private String picture;
    private String email;

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO(UserModel user) {
        this.fullname = user.getFullname();
        this.username = user.getName(); // <-- This gets actual 'username' field
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.picture = user.getPicture();
        this.email = user.getEmail();
    }

    // Getters & Setters
}

