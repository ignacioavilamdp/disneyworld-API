package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User-login")
public class UserDTOLogin {

    private String name;
    private String password;

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
}
