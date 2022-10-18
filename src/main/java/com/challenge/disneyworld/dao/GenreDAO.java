package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Genre;

import java.util.List;

public interface GenreDAO {

    /**
     * Retrieves a genre by its id.
     *
     * @param id the genre id.
     * @return the genre with the given id or null if the genre does not exist.
     */
    Genre getById(long id);

    /**
     * Retrieves a genre by its name.
     *
     * <p>The name must not be null.
     *
     * @param name the genre name.
     * @return the genre with the given name or null if the genre does not exist.
     */
    Genre getByName(String name);

    /**
     * Retrieves a list of all genres
     *
     * @return a list of all genres.
     */
    List<Genre> getAll();

    /**
     * Returns whether a genre with the given id exists.
     *
     * @param id the genre id
     * @return true if a genre with the given id exists, false otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a genre with the given name exists.
     *
     * <p>The name must not be null.
     *
     * @param name the genre name
     * @return true if a genre with the given name exists, false otherwise.
     */
    boolean existsByName(String name);
}
