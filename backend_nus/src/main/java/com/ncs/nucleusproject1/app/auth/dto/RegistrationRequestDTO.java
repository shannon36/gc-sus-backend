package com.ncs.nucleusproject1.app.auth.dto;

public class RegistrationRequestDTO {

    private String role;  // Role can be "S" for seller or "C" for customer
    private String name;

    // You can add other fields here if needed
    // Example: private String fullName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Add other getters and setters as needed
}
