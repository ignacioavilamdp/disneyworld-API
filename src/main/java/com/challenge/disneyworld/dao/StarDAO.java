package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface StarDAO {
    /**
     * Retrieves a star by its id.
     *
     * @param id the star id.
     * @return the star with the given id or null if the star does not exist.
     */
    Star getById(long id);

    /**
     * Retrieves a star by its name.
     *
     * <p>The name must not be null.
     *
     * @param name the star name.
     * @return the star with the given name or null if the star does not exist.
     */
    Star getByName(String name);

    /**
     * Saves the given star.
     *
     * <p>The star must not be null and must not be already saved.
     * There must not be another star with the same name already saved.
     *
     * <p>There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Returns the star saved as the save operations might have changed the
     * star instance.
     *
     * @param star the star instance to save.
     * @return the star instance saved.
     *
     */
    Star save(Star star);

    /**
     * Deletes the given star
     *
     * <p>The star must not be null and must be already saved.
     *
     * <p>There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * @param star the star instance to delete.
     */
    void delete(Star star);

    /**
     * Retrieves a list of all stars.
     *
     * @return a list of all stars.
     */
    List<Star> getAll();

    /**
     * Searches stars by name, age, weigh and movie.
     *
     * <p>The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process.
     *
     * @param name the star name.
     * @param age the star age.
     * @param weight the star weight.
     * @param movieId the movie id where the star may have appeared.
     * @return a list containing all stars saved that fulfill the search
     * criteria. If no star is found, then the list will be empty.
     */
    List<Star> search(String name, Short age, Float weight, Long movieId);

    /**
     * Retrieves a list of all contents related to a star by its id.
     *
     * <p>There must be a star previously saved with the id passed.
     *
     * @param id the star id.
     * @return a list containing all contents related to the star. If there is
     * no content related, then the list will be empty.
     */
    List<Content> getContentsById(Long id);

    /**
     * Returns whether a star with the given id exists.
     *
     * @param id the star id
     * @return true if a star with the given id exists, false otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a star with the given name exists.
     *
     * <p>The name must not be null.
     *
     * @param name the star name
     * @return true if a star with the given name exists, false otherwise.
     */
    boolean existsByName(String name);
}
