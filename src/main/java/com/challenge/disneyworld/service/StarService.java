package com.challenge.disneyworld.service;

import com.challenge.disneyworld.exceptions.*;
import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.models.dto.StarDetailDTO;

import java.util.List;

/**
 * Interface to serve Star related business logic using some underlying storage
 * mechanism.
 */
public interface StarService {

    /**
     * Searches stars by name, age, weigh and movie.
     * The search parameters may be null. If a parameter is null, then it
     * will not be taken into account in the search process.
     *
     * @param name the star name.
     * @param age the star age.
     * @param weight the star weight.
     * @param movieId the movie id.
     * @return a list containing all stars as basic DTO's that fulfill the
     * search criteria. If no star is found, then the list will be empty.
     */
    List<StarBaseDTO> search(String name, Short age, Float weight, Long movieId);

    /**
     * Retrieves a list of all stars.
     *
     * @return a list of all stars as detailed DTO's.
     */
    List<StarDetailDTO> getAll();

    /**
     * Retrieves a star by its id.
     *
     * @param id the star id.
     * @return the star as a detailed DTO.
     * @throws InvalidIdException if id is null.
     * @throws NonExistentEntityException if there is no star with the given id.
     */
    StarDetailDTO getById(Long id);

    /**
     * Updates the star instance with the given id using the data contained
     * in the given dto.
     *
     * <p>Postcondition: The star instance with the given id is updated
     * using the data values contained in the given dto. The star's
     * attributes are populated with the data contained in the given dto,
     * overriding its previous values.
     *
     * @param id the star id which data wants to be updated.
     * @param dto the dto containing the data to update the star instance.
     * @return the updated star instance as a detailed DTO.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no star with the given
     * id.
     * @throws InvalidDTOException if some data in the given dto is invalid:
     * the dto id is different from the id param, the name is null, there is
     * another star with the same name, some content in the contents list does
     * not exist.
     */
    StarDetailDTO updateById(Long id, StarDetailDTO dto);

    /**
     * Creates and saves a new star instance into the underlying storage
     * using the data contained in the given dto.
     *
     * <p>Postcondition: A new star instance is created and stored into the
     * underlying storage. The star's attributes are populated with the data
     * contained in the given dto.
     *
     * @param dto the dto containing the data to create a new star instance.
     * @return the saved star instance as a detailed DTO.
     * @throws InvalidDTOException if some data in the given dto is invalid:
     * the name is null, there is another star with the same name, some content
     * in the contents list does not exist.
     */
    StarDetailDTO save(StarDetailDTO dto);

    /**
     * Deletes the star instance with the given id.
     *
     * <p>Postcondition: The star instance with the given id is deleted
     * from the underlying storage.
     *
     * @param id the star id that wants to be removed.
     * @return a string message if the removed process was successful.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no star with the given
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
    String relateContent(Long starId, Long contentId);

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
    String unRelateContent(Long starId, Long contentId);

    /**
     * Retrieves a list of all contents related to a star by its id.
     *
     * @param id the star id.
     * @return a list containing all contents as base DTO's related to the star.
     * If there is no content related, then the list will be empty.
     * @throws InvalidIdException if the given id is null.
     * @throws NonExistentEntityException if there is no star with the given id.
     */
    List<ContentBaseDTO> getContentsById(Long id);
}
