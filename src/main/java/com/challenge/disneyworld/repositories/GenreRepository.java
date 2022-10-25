package com.challenge.disneyworld.repositories;

import com.challenge.disneyworld.models.domain.Genre;

import java.util.List;

/**
 * Interface to interact with an underlying storage mechanism designed to
 * persist Genre instances.
 */
public interface GenreRepository {

    /**
     * Retrieves a genre by its id.
     *
     * @param id the genre id.
     * @return the genre with the given id or null if the genre does not exist
     * in the storage.
     */
    Genre getById(long id);

    /**
     * Retrieves a genre by its name.
     *
     * <p>Precondition: The name must not be null.
     *
     * @param name the genre name.
     * @return the genre with the given name or null if the genre does not
     * exist in the storage.
     */
    Genre getByName(String name);

    /**
     * Retrieves a list of all genres
     *
     * @return a list of all genres contained in the storage.
     */
    List<Genre> getAll();

    /**
     * Returns whether a genre with the given id exists.
     *
     * @param id the genre id
     * @return true if a genre with the given id exists in the storage, false
     * otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a genre with the given name exists.
     *
     * <p>Precondition: The name must not be null.
     *
     * @param name the genre name
     * @return true if a genre with the given name exists in the storage, false
     * otherwise.
     */
    boolean existsByName(String name);
}
