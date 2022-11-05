package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.domain.Rating;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.dto.ContentDetailDTO;
import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.GenreRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides methods to map between {@link Content} and
 * {@link ContentBaseDTO} or {@link ContentDetailDTO}
 */
@Component
public class ContentMapper {

    /**
     * Returns a new {@link ContentBaseDTO} instance using the data contained
     * in the {@link Content} given.
     *
     * @param content the content instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the
     * content instance given, or null if the content is null.
     */
    public ContentBaseDTO entityToBaseDTO(Content content){
        ContentBaseDTO dto = null;
        if (content != null) {
            dto = new ContentBaseDTO();
            dto.setId(content.getId());
            dto.setTitle(content.getTitle());
            dto.setImage(content.getImage());
            dto.setDate(content.getDate());
        }
        return dto;
    }

    /**
     * Returns a new {@link ContentDetailDTO} instance using the data contained
     * in the {@link Content} given.
     *
     * @param content the content instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the
     * content instance given, or null if the content is null.
     */
    public ContentDetailDTO entityToDetailDTO(Content content){
        ContentDetailDTO dto = null;
        if (content != null){
            dto = new ContentDetailDTO();
            dto.setId(content.getId());
            dto.setTitle(content.getTitle());
            dto.setDate(content.getDate());
            dto.setRating(content.getRating().name());
            dto.setImage(content.getImage());
            dto.setGenre(content.getGenre().getName());
            List<String> starsNames = new ArrayList<>();
            for (Star star : content.getStars()){
                starsNames.add(star.getName());
            }
            dto.setStars(starsNames);
        }
        return dto;
    }

}
