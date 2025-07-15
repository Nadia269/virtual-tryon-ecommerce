package com.shop.shop.Model;

import org.springframework.stereotype.Component;
import jakarta.persistence.*;

@Component
@Entity
@Table(name="location")
public class LocationsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String state;
    private String city;
    private String zip;
    

    public LocationsModel() {}

    public LocationsModel(Long id, String country, String state, String city, String zip) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zip = zip;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
