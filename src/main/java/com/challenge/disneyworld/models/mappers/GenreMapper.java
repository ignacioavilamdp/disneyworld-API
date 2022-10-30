package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.dto.GenreBaseDTO;
import com.challenge.disneyworld.models.dto.GenreDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * A class that provides methods to map between {@link Genre} and
 * {@link GenreBaseDTO} or {@link GenreDetailDTO}
 */
@Component
public class GenreMapper {

    private final ContentMapper contentMapper;

    @Autowired
    public GenreMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    /**
     * Returns a new {@link GenreBaseDTO} instance using the data contained
     * in the {@link Genre} given.
     *
     * @param genre the genre instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the genre
     * instance given, or null if the genre is null.
     */
    public GenreBaseDTO entityToBaseDTO(Genre genre){
        GenreBaseDTO dto = null;
        if (genre != null) {
            dto = new GenreBaseDTO();
            dto.setId(genre.getId());
            dto.setName(genre.getName());
            dto.setImage(genre.getImage());
        }
        return dto;
    }

    /**
     * Returns a new {@link GenreDetailDTO} instance using the data contained
     * in the {@link Genre} given.
     *
     * @param genre the genre instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the genre
     * instance given, or null if the genre is null.
     */
    public GenreDetailDTO entityToDetailDTO(Genre genre){
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
                            map(content -> contentMapper.entityToBaseDTO(content)).
                            collect(Collectors.toList())
            );
        }
        return dto;
    }
}
