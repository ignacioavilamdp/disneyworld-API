package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.GenreBaseDTO;
import com.challenge.disneyworld.models.dto.GenreDetailDTO;

import java.util.List;

/**
 * Interface to provide information about genres stored in some underlying
 * storage.
 */
public interface GenreService {

    /**
     * Retrieves a list of all genres with basic information.
     *
     * @return a list of all genres as basic DTO's.
     */
    List<GenreBaseDTO> getAllBase();

    /**
     * Retrieves a list of all genres with detailed information.
     *
     * @return a list of all genres as detailed DTO's.
     */
    List<GenreDetailDTO> getAllDetail();
}
