package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.UserRegisterDTO;
import com.challenge.disneyworld.models.dto.UserLoginDTO;
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

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Obtain a detailed list of all users",
               description = "Obtains a detailed list of all users, including its hashed passwords.\n" +
                             "The user must be ADMIN")
    @GetMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "DisneyAPI")
    public ResponseEntity<List<UserRegisterDTO>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Register a new user",
              description = "Registers a new user.\n" +
                            "The name must be available, i.e. there must not be other user with the same name. \n" +
                            "The email address must be available, i.e. there must not be other user with the same email address. \n" +
                            "The available roles are 'ROLE_USER' and 'ROLE_ADMIN'.\n" +
                            "The user must be ADMIN.")

    @PostMapping("/register")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "DisneyAPI")
    public String registerUser(@RequestBody UserRegisterDTO dto){
        return service.register(dto);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO dto){
        return service.login(dto.getName(), dto.getPassword());
    }

}
