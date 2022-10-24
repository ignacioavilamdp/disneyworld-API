package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;

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
    List<GenreDTOBase> getAllBase();

    /**
     * Retrieves a list of all genres with detailed information.
     *
     * @return a list of all genres as detailed DTO's.
     */
    List<GenreDTODetail> getAllDetail();
}
