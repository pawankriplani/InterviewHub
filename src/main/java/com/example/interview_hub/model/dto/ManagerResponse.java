package com.example.interview_hub.model.dto;

public class ManagerResponse {
    private String fullName;
    private String email;
    
    // Constructor
    public ManagerResponse(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    // Default constructor
    public ManagerResponse() {}

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
