package com.shop.shop.Model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name= "contact")
public class ContactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; 

    private String email; 
  
    private String subject;
 
    private String msg;

    private LocalDateTime submittedAt;

    public ContactModel() {
    }

    public ContactModel(Long id, String name, String email, String subject, String msg, LocalDateTime submittedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.msg = msg;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getSubmittedAt() {
        return this.submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    @PrePersist
    public void prePersist() {
        submittedAt = LocalDateTime.now();
    }

}