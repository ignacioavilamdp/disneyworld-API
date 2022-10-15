package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User-register")
public class UserDTORegister {
    private String name;
    private String email;
    private String password;
    private String role;

    public UserDTORegister() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
