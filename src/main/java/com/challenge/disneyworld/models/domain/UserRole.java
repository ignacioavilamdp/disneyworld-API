package com.challenge.disneyworld.models.domain;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_USER;

    public String getAuthority() {
        return name();
    }
}
