package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;
import com.challenge.disneyworld.service.GenreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "genre", description = "disney genres")
@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService service;

    @ApiOperation("Obtain a of genres")
    @GetMapping()
    public ResponseEntity<List<GenreDTOBase>> getAllBase() {
        return ResponseEntity.ok().body(service.getAllBase());
    }

    @ApiOperation("Obtain a detailed list of all genres")
    @GetMapping("/detail")
    public ResponseEntity<List<GenreDTODetail>> getAllDetail() {
        return ResponseEntity.ok().body(service.getAllDetail());
    }
}
