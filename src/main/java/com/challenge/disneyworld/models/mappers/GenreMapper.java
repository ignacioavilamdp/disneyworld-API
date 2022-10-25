package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;

import java.util.stream.Collectors;

/**
 * A class that provides static methods to map from {@link Genre} to
 * {@link GenreDTOBase} or {@link GenreDTODetail}
 */
public class GenreMapper {

    public static GenreDTOBase domainToDTOBase(Genre genre){
        GenreDTOBase dto = null;
        if (genre != null) {
            dto = new GenreDTOBase();
            dto.setId(genre.getId());
            dto.setName(genre.getName());
            dto.setImage(genre.getImage());
        }
        return dto;
    }

    public static GenreDTODetail domainToDTODetail(Genre genre){
        GenreDTODetail dto = null;
        if (genre != null){
            dto = new GenreDTODetail();
            dto.setName(genre.getName());
            dto.setImage(genre.getImage());
            dto.setId(genre.getId());
            dto.setContents(
                    genre.
                    getContents().
                    stream().
                            map(content -> ContentMapper.domainToDTOBase(content)).
                            collect(Collectors.toList())
            );
        }
        return dto;
    }
}
