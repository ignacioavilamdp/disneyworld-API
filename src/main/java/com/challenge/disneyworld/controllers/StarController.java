package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.service.StarService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Api(tags = "character", description = "everything about disney characters")
@RestController
@RequestMapping("/characters")
public class StarController {

    @Autowired
    private StarService service;


    @ApiOperation("Search a character")
    @GetMapping
    public ResponseEntity<List<StarDTOBase>> search(
            @RequestParam(name = "name", required = false)  String name,
            @RequestParam(name = "age", required = false)   Short  age,
            @RequestParam(name = "weight", required = false)   Float  weight,
            @RequestParam(name = "movie", required = false) Long movieId){
        return ResponseEntity.ok(service.search(name, age, weight, movieId));
    }

    @ApiOperation("Add a new character")
    @PostMapping
    public ResponseEntity<StarDTODetail> save(@RequestBody StarDTODetail dto) {
        StarDTODetail savedDTO = service.save(dto);
        URI uri = URI.create("characters/"+ savedDTO.getId());
        return ResponseEntity.created(uri).body(savedDTO);
    }

    @ApiOperation("Obtain a detailed list of all characters")
    @GetMapping("/detail")
    public ResponseEntity<List<StarDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @ApiOperation("Find a character")
    @GetMapping("/{characterId}")
    public ResponseEntity<StarDTODetail> getById(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @ApiOperation("Delete a character")
    @DeleteMapping("/{characterId}")
    public ResponseEntity<String> deleteById(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.deleteById(id));
    }

    @ApiOperation("Update a character (including its movies)")
    @PutMapping("/{characterId}")
    public ResponseEntity<StarDTODetail> updateById(@PathVariable("characterId") Long id,
                                                @RequestBody StarDTODetail dto){
        StarDTODetail updatedDTO = service.updateById(id, dto);
        URI uri = URI.create("characters/"+ updatedDTO.getId());
        return ResponseEntity.accepted().location(uri).body(updatedDTO);
    }

    @ApiOperation("Find movies of a character")
    @GetMapping("/{characterId}/movies")
    public ResponseEntity<List<ContentDTOBase>> getContents(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.getContentsById(id));
    }

    @ApiOperation("Add a movie to a character")
    @PostMapping("/{characterId}/movies/{movieId}")
    public ResponseEntity<String> relateContent(@PathVariable("characterId") Long starId,
                                         @PathVariable("movieId") Long contentId){
        URI uri = URI.create("characters/" + starId + "movies/" + contentId);
        return ResponseEntity.created(uri).body(service.relateContent(starId, contentId));
    }

    @ApiOperation("Delete a movie from a character")
    @DeleteMapping("/{characterId}/movies/{movieId}")
    public ResponseEntity<String> unRelateContent(@PathVariable("characterId") Long starId,
                                         @PathVariable("movieId") Long contentId){
            return ResponseEntity.ok().body(service.unRelateContent(starId, contentId));
    }

}
