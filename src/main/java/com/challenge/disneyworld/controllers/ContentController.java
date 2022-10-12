package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.service.ContentService;
import com.challenge.disneyworld.service.StarService;
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

    @GetMapping
    public ResponseEntity<List<ContentDTOBase>> search(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "genre", required = false) Integer genreId,
            @RequestParam(name = "order", required = false) String order){
        return ResponseEntity.ok(service.search(title, genreId, true));
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
        if (service.deleteById(id))
            return ResponseEntity.ok().body("Deleted correctly");
        else
            return ResponseEntity.badRequest().body("Nothing deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDTODetail> update(@PathVariable("id") Long id,
                                                   @RequestBody ContentDTODetail dto){
        dto.setId(id);
        ContentDTODetail updatedDTO = service.update(dto);
        if (updatedDTO != null) {
            URI uri = URI.create("movies/"+ updatedDTO.getId());
            return ResponseEntity.accepted().location(uri).body(updatedDTO);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ContentDTODetail> save(@RequestBody ContentDTODetail dto) {
        ContentDTODetail savedDTO = service.save(dto);
        if (savedDTO != null) {
            URI uri = URI.create("movies/"+ savedDTO.getId());
            return ResponseEntity.accepted().location(uri).body(savedDTO);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/characters")
    public ResponseEntity<List<StarDTOBase>> getStars(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(service.getStarsById(id));
    }

    @PostMapping("/{contentId}/characters/{starId}")
    public ResponseEntity<String> relateStar(@PathVariable("starId") Long starId,
                                                @PathVariable("contentId") Long contentId){
        if (service.relateStar(starId, contentId)){
            URI uri = URI.create("movies/" + contentId + "characters/" + starId);
            return ResponseEntity.accepted().
                    location(uri).body("Relationship established");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{contentId}/characters/{starId}")
    public ResponseEntity<String> unRelateStar(@PathVariable("starId") Long starId,
                                                  @PathVariable("contentId") Long contentId){
        if (service.unRelateStar(starId, contentId)){
            URI uri = URI.create("movies/" + contentId + "characters/");
            return ResponseEntity.accepted().
                    location(uri).body("Relationship removed");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
