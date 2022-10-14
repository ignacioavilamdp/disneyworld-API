package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Api(tags = "movie", description = "everything about disney movies")
@RestController
@RequestMapping("/movies")
public class ContentController {

    @Autowired
    private ContentService service;

    @ApiOperation("Search a movie")
    @GetMapping
    public ResponseEntity<List<ContentDTOBase>> search(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "genre", required = false) Integer genreId,
            @RequestParam(name = "order", required = false) String order){
        return ResponseEntity.ok(service.search(title, genreId, true));
    }

    @ApiOperation("Add a new movie")
    @PostMapping
    public ResponseEntity<ContentDTODetail> save(@RequestBody ContentDTODetail dto) {
        ContentDTODetail savedDTO = service.save(dto);
        URI uri = URI.create("movies/"+ savedDTO.getId());
        return ResponseEntity.created(uri).body(savedDTO);
    }

    @ApiOperation("Obtain a detailed list of all movies")
    @GetMapping("/detail")
    public ResponseEntity<List<ContentDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @ApiOperation("Find a movie")
    @GetMapping("/{movieId}")
    public ResponseEntity<ContentDTODetail> getById(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @ApiOperation("Delete a movie")
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteById(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.deleteById(id));
    }

    @ApiOperation("Update a movie (including its characters)")
    @PutMapping("/{movieId}")
    public ResponseEntity<ContentDTODetail> updateById(@PathVariable("movieId") Long id,
                                                   @RequestBody ContentDTODetail dto){
        ContentDTODetail updatedDTO = service.updateById(id, dto);
        URI uri = URI.create("movies/"+ updatedDTO.getId());
        return ResponseEntity.accepted().location(uri).body(updatedDTO);
    }

    @ApiOperation("Find characters of a movie")
    @GetMapping("/{movieId}/characters")
    public ResponseEntity<List<StarDTOBase>> getStars(@PathVariable("movieId") Long id){
        return ResponseEntity.ok().body(service.getStarsById(id));
    }

    @ApiOperation("Add a character to a movie")
    @PostMapping("/{movieId}/characters/{characterId}")
    public ResponseEntity<String> relateStar(@PathVariable("characterId") Long starId,
                                                @PathVariable("movieId") Long contentId){
        URI uri = URI.create("movies/" + contentId + "characters/" + starId);
        return ResponseEntity.created(uri).body(service.relateStar(starId, contentId));
    }

    @ApiOperation("Delete a character from a movie")
    @DeleteMapping("/{movieId}/characters/{characterId}")
    public ResponseEntity<String> unRelateStar(@PathVariable("characterId") Long starId,
                                                  @PathVariable("movieId") Long contentId){
        return ResponseEntity.ok().body(service.unRelateStar(starId, contentId));
    }

}
