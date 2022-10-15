package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.UserDTORegister;
import com.challenge.disneyworld.models.dto.UserDTOLogin;
import com.challenge.disneyworld.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "user", description = "user authentication")
public class UserController {

    @Autowired
    UserService service;

    @Operation(summary = "Obtain a detailed list of all users")
    @GetMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "DisneyAPI")
    public ResponseEntity<List<UserDTORegister>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "DisneyAPI")
    public String registerUser(@RequestBody UserDTORegister dto){
        return service.register(dto);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public String login(@RequestBody UserDTOLogin dto){
        return service.login(dto.getName(), dto.getPassword());
    }

}
