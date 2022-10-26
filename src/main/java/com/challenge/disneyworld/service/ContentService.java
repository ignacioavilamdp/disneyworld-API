package com.challenge.disneyworld.service;

import com.challenge.disneyworld.exceptions.*;
import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.dto.ContentDetailDTO;
import com.challenge.disneyworld.models.dto.StarBaseDTO;

import java.util.List;

/**
 * Interface to serve Content related business logic using some underlying
 * storage mechanism.
 */
public interface ContentService {

    /**
     * Searches contents by title and genre.
     * The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process. <br>
     * If the order parameter is 'ASC', the returned list will be ordered in
     * lexicographical ascending order. If 'DESC', then the order will be
     * descending. If null, then 'ASC' will be considered.
     *
     * @param title the content title.
     * @param genreId the genre id.
     * @param order the order criteria.
     * @return a list containing all contents as basic DTO's that fulfill the
     * search criteria. If no content is found, then the list will be empty.
     * @throws InvalidOrderCriteriaException if the order parameter is
     * different from 'ASC', 'DESC' or null.
     */
    List<ContentBaseDTO> search(String title, Integer genreId, String order);

    /**
     * Retrieves a list of all contents.
     *
     * @return a list of all contents as detailed DTO's.
     */
    List<ContentDetailDTO> getAll();

    /**
     * Retrieves a content by its id.
     *
     * @param id the content id.
     * @return the content as a detailed DTO.
     * @throws InvalidIdException if id is null.
     * @throws NonExistentEntityException if there is no movie with the given id.
     */
    ContentDetailDTO getById(Long id);

    /**
     * Updates the content instance with the given id using the data contained
     * in the given dto.
     *
     * <p>Postcondition: The content instance with the given id is updated
     * using the data values contained in the given dto. The content's
     * attributes are populated with the data contained in the given dto,
     * overriding its previous values.
     *
     * @param id the content id which data wants to be updated.
     * @param dto the dto containing the data to update the content instance.
     * @return the updated content instance as a detailed DTO.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no movie with the given
     * id.
     * @throws InvalidDTOException if some data in the given dto is invalid:
     * the dto id is different from the id param, the title or rating are null,
     * the rating is not valid, there is another movie with the same title,
     * there is no genre with the given genre name, some character in the
     * characters list does not exist.
     */
    ContentDetailDTO updateById(Long id, ContentDetailDTO dto);

    /**
     * Creates and saves a new content instance into the underlying storage
     * using the data contained in the given dto.
     *
     * <p>Postcondition: A new content instance is created and stored into the
     * underlying storage. The content's attributes are populated with the data
     * contained in the given dto.
     *
     * @param dto the dto containing the data to create a new content instance.
     * @return the saved content instance as a detailed DTO.
     * @throws InvalidDTOException if some data in the given dto is invalid:
     * the title or rating are null, the rating is not valid, there is another
     * movie with the same title, there is no genre with the given genre name,
     * some character in the characters list does not exist.
     */
    ContentDetailDTO save(ContentDetailDTO dto);

    /**
     * Deletes the content instance with the given id.
     *
     * <p>Postcondition: The content instance with the given id is deleted
     * from the underlying storage.
     *
     * @param id the content id that wants to be removed.
     * @return a string message if the removed process was successful.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no movie with the given
     * id.
     */
    String deleteById(Long id);

    /**
     * Establishes a relationship between a star and a content.
     *
     * <p>Postcondition: A relationship is established between the star and the
     * content. The star is added to the content's stars list and the content
     * is added to the star's contents list.
     *
     * @param starId the star id.
     * @param contentId the content id.
     * @return a string message if the process was successful.
     * @throws InvalidIdException if the given star or content id is null.
     * @throws NonExistentEntityException if there is no star or content with
     * the given id's.
     * @throws DuplicateRelationException if the star and the content are
     * already related.
     */
    String relateStar(Long starId, Long contentId);

    /**
     * Removes the relationship between a star and a content.
     *
     * <p>Postcondition: The relationship between the star and the content
     * is removed. The star is removed from the content's stars list and the
     * content is removed to the star's contents list.
     *
     * @param starId the star id.
     * @param contentId the content id.
     * @return a string message if the process was successful.
     * @throws InvalidIdException if the given star or content id is null.
     * @throws NonExistentEntityException if there is no star or content with
     * the given id's.
     * @throws NonExistentRelationException if the star and the content are
     * already unrelated.
     */
    String unRelateStar(Long starId, Long contentId);

    /**
     * Retrieves a list of all stars related to a content by its id.
     *
     * @param id the content id.
     * @return a list containing all stars as base DTO's related to the content.
     * If there is no star related, then the list will be empty.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no movie with the given id.
     */
    List<StarBaseDTO> getStarsById(Long id);
}
