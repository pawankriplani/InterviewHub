package com.example.interview_hub.model.dto;

public class ManagerResponse {
    private String name;
    private String email;
    
    // Constructor
    public ManagerResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Default constructor
    public ManagerResponse() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
