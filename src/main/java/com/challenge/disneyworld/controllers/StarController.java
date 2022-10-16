package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.service.StarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/characters")
@Tag(name = "character", description = "everything about disney characters")
@SecurityRequirement(name = "DisneyAPI")
public class StarController {

    @Autowired
    private StarService service;

    @Operation(summary = "Search a character")
    @GetMapping
    public ResponseEntity<List<StarDTOBase>> search(
            @RequestParam(name = "name", required = false)  String name,
            @RequestParam(name = "age", required = false)   Short  age,
            @RequestParam(name = "weight", required = false) Float weight,
            @RequestParam(name = "movie", required = false) Long movieId){
        return ResponseEntity.ok(service.search(name, age, weight, movieId));
    }

    @Operation(summary = "Add a new character",
               description = "Adds a new character, including its list of movies.\n" +
                             "The movies passed in the list must be already included in the movies section.\n" +
                             "The name must be available, i.e. there must not be other character with the same name. \n" +
                             "The id in the payload is not taken into account.\n" +
                             "The user must be ADMIN")
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<StarDTODetail> save(@RequestBody StarDTODetail dto) {
        StarDTODetail savedDTO = service.save(dto);
        URI uri = URI.create("characters/"+ savedDTO.getId());
        return ResponseEntity.created(uri).body(savedDTO);
    }

    @Operation(summary = "Obtain a detailed list of all characters")
    @GetMapping("/detail")
    public ResponseEntity<List<StarDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Find a character")
    @GetMapping("/{characterId}")
    public ResponseEntity<StarDTODetail> getById(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @Operation(summary = "Delete a character",
               description = "Deletes a character.\n" +
                             "The user must be ADMIN")
    @DeleteMapping("/{characterId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteById(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.deleteById(id));
    }

    @Operation(summary = "Update a character",
               description = "Updates a character, including its list of movies.\n" +
                             "The movies passed in the list must be already included in the movies section.\n" +
                             "The name must be available, i.e. there must not be other character with the same name. \n" +
                             "The id in the payload must match the id in the URI.\n" +
                             "The user must be ADMIN")
    @PutMapping("/{characterId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<StarDTODetail> updateById(@PathVariable("characterId") Long id,
                                                @RequestBody StarDTODetail dto){
        StarDTODetail updatedDTO = service.updateById(id, dto);
        URI uri = URI.create("characters/"+ updatedDTO.getId());
        return ResponseEntity.accepted().location(uri).body(updatedDTO);
    }

    @Operation(summary = "Find movies of a character")
    @GetMapping("/{characterId}/movies")
    public ResponseEntity<List<ContentDTOBase>> getContents(@PathVariable("characterId") Long id){
        return ResponseEntity.ok().body(service.getContentsById(id));
    }

    @Operation(summary = "Add a single movie to a character",
               description = "Adds a single movie to a character.\n" +
                             "The user must be ADMIN.")
    @PostMapping("/{characterId}/movies/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> relateContent(@PathVariable("characterId") Long starId,
                                         @PathVariable("movieId") Long contentId){
        URI uri = URI.create("characters/" + starId + "movies/" + contentId);
        return ResponseEntity.created(uri).body(service.relateContent(starId, contentId));
    }

    @Operation(summary = "Remove a single movie from a character",
               description = "Removes a single movie from a character.\n" +
                              "The user must be ADMIN.")
    @DeleteMapping("/{characterId}/movies/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> unRelateContent(@PathVariable("characterId") Long starId,
                                         @PathVariable("movieId") Long contentId){
            return ResponseEntity.ok().body(service.unRelateContent(starId, contentId));
    }

}
