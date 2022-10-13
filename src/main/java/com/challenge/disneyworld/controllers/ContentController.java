package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.service.ContentService;
import com.challenge.disneyworld.service.StarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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


    @PostMapping
    public ResponseEntity<ContentDTODetail> save(@RequestBody ContentDTODetail dto) {
        ContentDTODetail savedDTO = service.save(dto);
        URI uri = URI.create("movies/"+ savedDTO.getId());
        return ResponseEntity.created(uri).body(savedDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ContentDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDTODetail> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(service.deleteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDTODetail> updateById(@PathVariable("id") Long id,
                                                   @RequestBody ContentDTODetail dto){
        ContentDTODetail updatedDTO = service.updateById(id, dto);
        URI uri = URI.create("movies/"+ updatedDTO.getId());
        return ResponseEntity.accepted().location(uri).body(updatedDTO);
    }

    @GetMapping("/{id}/characters")
    public ResponseEntity<List<StarDTOBase>> getStars(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(service.getStarsById(id));
    }

    @PostMapping("/{id}/characters/{characterId}")
    public ResponseEntity<String> relateStar(@PathVariable("characterId") Long starId,
                                                @PathVariable("id") Long contentId){
        URI uri = URI.create("movies/" + contentId + "characters/" + starId);
        return ResponseEntity.created(uri).body(service.relateStar(starId, contentId));
    }

    @DeleteMapping("/{id}/characters/{characterId}")
    public ResponseEntity<String> unRelateStar(@PathVariable("characterId") Long starId,
                                                  @PathVariable("id") Long contentId){
        return ResponseEntity.ok().body(service.unRelateStar(starId, contentId));
    }

}
