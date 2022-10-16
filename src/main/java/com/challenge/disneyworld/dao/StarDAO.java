package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface StarDAO {
    Star getById(long id);
    Star getByName(String name);
    /**
     * Saves the given entity. Returns the entity saved as the save operations
     * might have changed the entity instance.
     *
     * @param star - the star to save
     * @return the star added
     */
    Star save(Star star);
    void delete(Star star);

    List<Star> getAll();
    List<Star> search(String name, Short age, Float weight, Long movie);
    List<Content> getContentsById(Long id);

    boolean existsById(long id);
    boolean existsByName(String name);
}
