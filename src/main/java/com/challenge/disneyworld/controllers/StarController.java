package com.challenge.disneyworld.controllers;

import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class StarController {

    @Autowired
    private StarService service;

    @GetMapping
    public ResponseEntity<List<StarDTOBase>> search(
            @RequestParam(name = "name", required = false)  String name,
            @RequestParam(name = "age", required = false)   Short  age,
            @RequestParam(name = "movie", required = false) String movie){
        return ResponseEntity.ok(service.search(name, age, movie));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StarDTODetail>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarDTODetail> getById(@PathVariable("id") Long id){
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
    public ResponseEntity<StarDTODetail> update(@PathVariable("id") Long id,
                                                @RequestBody StarDTODetail dto){
        dto.setId(id);
        StarDTODetail updatedDTO = service.update(dto);
        if (updatedDTO != null) {
            URI uri = URI.create("characters/"+ updatedDTO.getId());
            return ResponseEntity.accepted().location(uri).body(updatedDTO);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<StarDTODetail> save(@RequestBody StarDTODetail dto) {
        StarDTODetail savedDTO = service.save(dto);
        if (savedDTO != null) {
            URI uri = URI.create("characters/"+ savedDTO.getId());
            return ResponseEntity.accepted().location(uri).body(savedDTO);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
