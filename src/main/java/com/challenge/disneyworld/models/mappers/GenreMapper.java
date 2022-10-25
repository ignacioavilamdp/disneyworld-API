package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.dto.GenreBaseDTO;
import com.challenge.disneyworld.models.dto.GenreDetailDTO;

import java.util.stream.Collectors;

/**
 * A class that provides static methods to map from {@link Genre} to
 * {@link GenreBaseDTO} or {@link GenreDetailDTO}
 */
public class GenreMapper {

    public static GenreBaseDTO entityToBaseDTO(Genre genre){
        GenreBaseDTO dto = null;
        if (genre != null) {
            dto = new GenreBaseDTO();
            dto.setId(genre.getId());
            dto.setName(genre.getName());
            dto.setImage(genre.getImage());
        }
        return dto;
    }

    public static GenreDetailDTO entityToDetailDTO(Genre genre){
        GenreDetailDTO dto = null;
        if (genre != null){
            dto = new GenreDetailDTO();
            dto.setName(genre.getName());
            dto.setImage(genre.getImage());
            dto.setId(genre.getId());
            dto.setContents(
                    genre.
                    getContents().
                    stream().
                            map(content -> ContentMapper.entityToBaseDTO(content)).
                            collect(Collectors.toList())
            );
        }
        return dto;
    }
}
