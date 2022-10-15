package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;
import com.challenge.disneyworld.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Tag(name = "genre", description = "disney movie genres")
@SecurityRequirement(name = "DisneyAPI")
public class GenreController {

    @Autowired
    private GenreService service;

    @Operation(summary = "Obtain a list of genres")
    @GetMapping()
    public ResponseEntity<List<GenreDTOBase>> getAllBase() {
        return ResponseEntity.ok().body(service.getAllBase());
    }

    @Operation(summary = "Obtain a detailed list of all genres")
    @GetMapping("/detail")
    public ResponseEntity<List<GenreDTODetail>> getAllDetail() {
        return ResponseEntity.ok().body(service.getAllDetail());
    }
}
