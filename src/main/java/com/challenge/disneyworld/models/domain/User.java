package com.challenge.disneyworld.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "APPUSER")
public class User {
    /*
        CREATE TABLE APPUSER
        (
            ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
            NAME        VARCHAR(45) NOT NULL UNIQUE,
            EMAIL       VARCHAR(45) NOT NULL,
            PASSWORD    VARCHAR(45) NOT NULL,
            CONSTRAINT  PK_USER PRIMARY KEY(ID)
        );
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    public User() {
    }

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
}
