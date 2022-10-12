package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.domain.Star;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenreDAO {

    Genre getById(long id);

    @Transactional
    Genre getByName(String name);

    List<Genre> getAll();
    boolean isById(long id);
    boolean isByName(String name);
}
