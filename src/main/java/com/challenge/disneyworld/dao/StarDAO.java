package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface StarDAO {


    public Star getById(long id);
    public Star getByName(String name);

    public List<Star> getAll();
    public List<Star> search(String name, Short age, String movie);

    /**
     * Saves the given entity. Returns the entity saved as the save operations
     * might have changed the entity instance.
     *
     * @param star - the star to save
     * @return the star added
     */
    public Star save(Star star);
    public Star update(Star star);

    public boolean isById(long id);
    public boolean isByName(String name);

    public void deleteById(long id);
}
