package com.example.testdoubles.fake;

import java.time.LocalDateTime;

/**
 * Model User
 */
public class User {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    
    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    
    // Gettery i settery
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

