package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * A login Data Transfer Object (DTO) of a
 * {@link com.challenge.disneyworld.models.domain.User}.
 */
@Schema(name = "User-login")
public class UserLoginDTO implements Serializable {

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
