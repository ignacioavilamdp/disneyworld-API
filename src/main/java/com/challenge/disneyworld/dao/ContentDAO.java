package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

/**
 * Interface to interact with an underlying storage mechanism designed to
 * persist Content instances.
 */
public interface ContentDAO {
    /**
     * Retrieves a content by its id.
     *
     * @param id the content id.
     * @return the content with the given id or null if the content does not
     * exist in the storage.
     */
    Content getById(long id);

    /**
     * Retrieves a content by its title.
     *
     * <p>Precondition: The title must not be null.
     *
     * @param title the content title.
     * @return the content with the given title or null if the content does not
     * exist in the storage.
     */
    Content getByTitle(String title);

    /**
     * Saves the given content instance.
     * Returns the content saved as the save operations might have changed the
     * content instance.
     *
     * <p>Precondition: The content must not be null. The content instance
     * must not already exist in the storage. The content title, rating and
     * genre must not be null. The content rating must be one of the Rating enum.
     * The genre must exist in the genre storage. The stars contained in the
     * stars list must exist in the star storage. There must not be another
     * content with the same title in the storage.
     *
     * <p>Precondition: There must be a transaction when invoked on a
     * container-managed entity manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Postcondition: The given content instance is persisted into the
     * underlying storage. The returned instance is in a managed state by the
     * persistence context.
     *
     * @param content the content instance to save into the storage.
     * @return the content instance saved into the storage.
     */
    Content save(Content content);

    /**
     * Deletes the given content instance.
     *
     * <p>Precondition: The content must not be null. The content instance
     * must already exist in the storage.
     *
     * <p>Precondition: There must be a transaction when invoked on a
     * container-managed entity manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Postcondition: The given content instance is deleted from the
     * underlying storage.
     *
     * @param content the content instance to delete from the storage.
     */
    void delete(Content content);

    /**
     * Retrieves a list of all contents.
     *
     * @return a list of all contents contained in the storage.
     */
    List<Content> getAll();

    /**
     * Searches contents by title and genre.
     * The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process. <br>
     * If the order parameter is 'ASC', the returned list will be ordered in
     * lexicographical ascending order. If 'DESC', then the order will be
     * descending. If null, then 'ASC' will be considered.
     *
     * <p>Precondition: The order parameter must be null, 'ASC' or 'DESC'.
     *
     * <p>Postcondition: The returned list is ordered by the specified order.
     *
     * @param title the content title.
     * @param genreId the genre id.
     * @param order the order criteria.
     * @return a list containing all contents in the storage that fulfill the
     * search criteria ordered by the specify order. If no content is found,
     * then the list will be empty.
     */
    List<Content> search(String title, Integer genreId, String order);

    /**
     * Retrieves a list of all stars related to a content by its id.
     *
     * <p>Precondition: There must exist a content with the given id in the
     * storage.
     *
     * @param id the content id.
     * @return a list containing all stars related to the content. If there is
     * no star related, then the list will be empty.
     */
    List<Star> getStarsById(long id);

    /**
     * Returns whether a content with the given id exists.
     *
     * @param id the content id.
     * @return true if a content with the given id exists in the storage, false
     * otherwise.
     */
    boolean existsById(long id);

    /**
     * Returns whether a content with the given title exists.
     *
     * <p>Precondition: The title must not be null.
     *
     * @param title the content title.
     * @return true if a content with the given title exists in the storage,
     * false otherwise.
     */
    boolean existsByTitle(String title);
}
