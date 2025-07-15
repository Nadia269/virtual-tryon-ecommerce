package com.shop.shop.Dtos;

import java.time.LocalDateTime;

public class CommentResponse {
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CommentResponse(String content, String username, LocalDateTime createdAt) {
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

  
}
