package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

/**
 * Interface to interact with an underlying storage mechanism designed to
 * persist Star instances.
 */
public interface StarDAO {
    /**
     * Retrieves a star by its id.
     *
     * @param id the star id.
     * @return the star with the given id or null if the star does not exist in
     * the storage.
     */
    Star getById(long id);

    /**
     * Retrieves a star by its name.
     *
     * <p>Precondition: The name must not be null.
     *
     * @param name the star name.
     * @return the star with the given name or null if the star does not exist
     * in the storage.
     */
    Star getByName(String name);

    /**
     * Saves the given star instance.
     * Returns the star saved as the save operations might have changed the
     * star instance.
     *
     * <p>Precondition: The content star must not be null. The star instance
     * must not already exist in the storage. The contents contained in the
     * contents list must exist in the content storage. There must not be
     * another star with the same name in the storage.
     *
     * <p>Precondition: There must be a transaction when invoked on a
     * container-managed entity manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Postcondition: The given star instance is persisted into the
     * underlying storage. The returned instance is in a managed state by the
     * persistence context.
     *
     * @param star the star instance to save into the storage.
     * @return the star instance saved into the storage.
     */
    Star save(Star star);

    /**
     * Deletes the given star instance.
     *
     * <p>Precondition: The star must not be null. The star instance must already exist in the
     * storage
     *
     * <p>Precondition: There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * @param star the star instance to delete from the storage.
     */
    void delete(Star star);

    /**
     * Retrieves a list of all stars.
     *
     * @return a list of all stars contained in the storage.
     */
    List<Star> getAll();

    /**
     * Searches stars by name, age, weigh and movie.
     * The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process.
     *
     * @param name the star name.
     * @param age the star age.
     * @param weight the star weight.
     * @param movieId the movie id where the star may have appeared.
     * @return a list containing all stars in the storage that fulfill the
     * search criteria. If no star is found, then the list will be empty.
     */
    List<Star> search(String name, Short age, Float weight, Long movieId);

    /**
     * Retrieves a list of all contents related to a star by its id.
     *
     * <p>Precondition: There must be a star with the given id in the storage.
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
     * @return true if a star with the given id exists in the storage, false
     * otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a star with the given name exists.
     *
     * <p>The name must not be null.
     *
     * @param name the star name
     * @return true if a star with the given name exists in the storage, false
     * otherwise.
     */
    boolean existsByName(String name);
}
