package com.challenge.disneyworld.models.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

    private final String role;

    public UserRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
