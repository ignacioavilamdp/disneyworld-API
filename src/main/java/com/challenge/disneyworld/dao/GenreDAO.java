package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Genre;

import java.util.List;

public interface GenreDAO {

    Genre getById(long id);
    Genre getByName(String name);

    List<Genre> getAll();

    boolean existsById(long id);
    boolean existsByName(String name);
}
