package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface StarDAO {
    Star getById(long id);
    Star getByName(String name);

    List<Star> getAll();
    List<Star> search(String name, Short age, Float weight, Long movie);

    /**
     * Saves the given entity. Returns the entity saved as the save operations
     * might have changed the entity instance.
     *
     * @param star - the star to save
     * @return the star added
     */
    Star save(Star star);
    Star update(Star star);

    boolean isById(long id);
    boolean isByName(String name);

    boolean deleteById(long id);

    List<Content> getContentsById(Long id);

}
