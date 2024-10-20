package com.ncs.nucleusproject1.app.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationRequestDTO {

    @JsonProperty("id_token")
    private String idToken;

    private String role;  // Role can be "S" for seller or "C" for customer
    private String name;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

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
