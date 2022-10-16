package com.challenge.disneyworld.models.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APPUSER")
public class User {
    /*
        CREATE TABLE APPUSER
        (
            ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
            NAME        VARCHAR(255) NOT NULL UNIQUE,
            EMAIL       VARCHAR(45) NOT NULL UNIQUE,
            PASSWORD    VARCHAR(255) NOT NULL,
            ROLE        ENUM('ROLE_ADMIN','ROLE_USER) NOT NULL,
            CONSTRAINT  PK_USER PRIMARY KEY(ID)
        );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
