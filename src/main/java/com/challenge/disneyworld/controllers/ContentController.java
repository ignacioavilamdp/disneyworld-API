package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.service.ContentService;
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
@RequestMapping("/movies")
@Tag(name = "movie", description = "everything about disney movies")
@SecurityRequirement(name = "DisneyAPI")
public class ContentController {

    @Autowired
    private ContentService service;

    @Operation(summary = "Search a movie")
    @GetMapping
    public ResponseEntity<List<ContentDTOBase>> search(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "genreId", required = false) Integer genreId,
            @RequestParam(name = "order", required = false) String order){
        return ResponseEntity.ok(service.search(title, genreId, true));
    }

    @Operation(summary = "Add a new movie",
               description = "Adds a new movie, including its list of characters.\n" +
                             "The characters passed in the list must be already included in the characters section.\n" +
                             "The genre must be already included in the genres section.\n" +
                             "The title must be available, i.e. there must not be other movie with the same title. \n" +
                             "The rating must be '1', '2', '3', '4' or '5'.\n" +
                             "The id in the payload is not taken into account.\n" +
                             "The user must be ADMIN")
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ContentDTODetail> save(@RequestBody ContentDTODetail dto) {
        ContentDTODetail savedDTO = service.save(dto);
        URI uri = URI.create("movies/"+ savedDTO.getId());
        return ResponseEntity.created(uri).body(savedDTO);
    }

    @Operation(summary = "Obtain a detailed list of all movies")
    @GetMapping("/detail")
    public ResponseEntity<List<ContentDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Find a movie")
    @GetMapping("/{movieId}")
    public ResponseEntity<ContentDTODetail> getById(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @Operation(summary = "Delete a movie",
               description = "Deletes a movie.\n" +
                             "The user must be ADMIN")
    @DeleteMapping("/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteById(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.deleteById(id));
    }

    @Operation(summary = "Update a movie",
               description = "Updates a movie, including its list of characters.\n" +
                             "The characters passed in the list must be already included in the characters section.\n" +
                             "The genre must be already included in the genres section.\n" +
                             "The title must be available, i.e. there must not be other movie with the same title. \n" +
                             "The rating must be '1', '2', '3', '4' or '5'.\n" +
                             "The id in the payload must match the id in the URI.\n" +
                             "The user must be ADMIN")
    @PutMapping("/{movieId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ContentDTODetail> updateById(@PathVariable("movieId") Long id,
                                                   @RequestBody ContentDTODetail dto){
        ContentDTODetail updatedDTO = service.updateById(id, dto);
        URI uri = URI.create("movies/"+ updatedDTO.getId());
        return ResponseEntity.accepted().location(uri).body(updatedDTO);
    }

    @Operation(summary = "Find all characters that participated in a movie")
    @GetMapping("/{movieId}/characters")
    public ResponseEntity<List<StarDTOBase>> getStars(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.getStarsById(id));
    }

    @Operation(summary = "Add a single character to a movie",
               description = "Adds a single character to a movie.\n" +
                             "The user must be ADMIN.")
    @PostMapping("/{movieId}/characters/{characterId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> relateStar(@PathVariable("characterId") Long starId,
                                                @PathVariable("movieId") Long contentId){
        URI uri = URI.create("movies/" + contentId + "characters/" + starId);
        return ResponseEntity.created(uri).body(service.relateStar(starId, contentId));
    }

    @Operation(summary = "Remove a single character from a movie",
               description = "Removes a single character from a movie.\n" +
                             "The user must be ADMIN.")
    @DeleteMapping("/{movieId}/characters/{characterId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> unRelateStar(@PathVariable("characterId") Long starId,
                                                  @PathVariable("movieId") Long contentId){
        return ResponseEntity.ok().body(service.unRelateStar(starId, contentId));
    }

}
