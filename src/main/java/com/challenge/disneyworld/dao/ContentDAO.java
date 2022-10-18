package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface ContentDAO {
    /**
     * Retrieves a content by its id.
     *
     * @param id the content id.
     * @return the content with the given id or null if the content does not exist.
     */
    Content getById(long id);

    /**
     * Retrieves a content by its title.
     *
     * <p>The title must not be null.
     *
     * @param title the content title.
     * @return the content with the given title or null if the content does not exist.
     */
    Content getByTitle(String title);

    /**
     * Saves the given content.
     *
     * <p>The content must not be null and must not be already saved.
     * There must not be another content with the same title already saved.
     *
     * <p>There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Returns the content saved as the save operations might have changed the
     * content instance.
     *
     * @param content the content instance to save.
     * @return the content instance saved.
     */
    Content save(Content content);

    /**
     * Deletes the given content
     *
     * <p>The content must not be null and must be already saved.
     *
     * <p>There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * @param content the content instance to delete
     */
    void delete(Content content);

    /**
     * Retrieves a list of all contents.
     *
     * @return a list of all contents.
     */
    List<Content> getAll();

    /**
     * Searches contents by title and genre.
     *
     * <p>The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process.
     *
     * <p>The order parameter must be null, 'ASC' or 'DESC'. If 'ASC', the list
     * will be ordered in lexicographical ascending order. If 'DESC', then the
     * order will be descending. If null, then 'ASC' will be considered.
     *
     * @param title the content title.
     * @param genreId the genre id.
     * @param order the order criteria.
     * @return a list containing all stars saved that fulfill the search
     * criteria. If no star is found, then the list will be empty.
     */
    List<Content> search(String title, Integer genreId, String order);

    /**
     * Retrieves a list of all stars related to a content by its id.
     *
     * <p>There must be a content previously saved with the id passed.
     *
     * @param id the content id.
     * @return a list containing all stars related to the content. If there is
     * no star related, then the list will be empty.
     */
    List<Star> getStarsById(Long id);

    /**
     * Returns whether a content with the given id exists.
     *
     * @param id the content id.
     * @return true if a content with the given id exists, false otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a content with the given title exists.
     *
     * <p>The title must not be null.
     *
     * @param title the content title.
     * @return true if a content with the given title exists, false otherwise.
     */
    boolean existsByTitle(String title);
}
