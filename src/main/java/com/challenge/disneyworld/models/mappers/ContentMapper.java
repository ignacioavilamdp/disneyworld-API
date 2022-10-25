package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.dto.ContentDetailDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides static methods to map from {@link Content} to
 * {@link ContentBaseDTO} or {@link ContentDetailDTO}
 */
public class ContentMapper {

    public static ContentBaseDTO entityToBaseDTO(Content content){
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

    public static ContentDetailDTO entityToDetailDTO(Content content){
        ContentDetailDTO dto = null;
        if (content != null){
            dto = new ContentDetailDTO();
            dto.setId(content.getId());
            dto.setTitle(content.getTitle());
            dto.setDate(content.getDate());
            dto.setRating(content.getRating());
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
