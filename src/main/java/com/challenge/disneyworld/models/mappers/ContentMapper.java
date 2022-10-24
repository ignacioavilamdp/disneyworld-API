package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provide static methods to map from {@link Content} to
 * {@link ContentDTOBase} or {@link ContentDTODetail}
 */
public class ContentMapper {

    public static ContentDTOBase domainToDTOBase(Content content){
        ContentDTOBase dto = null;
        if (content != null) {
            dto = new ContentDTOBase();
            dto.setId(content.getId());
            dto.setTitle(content.getTitle());
            dto.setImage(content.getImage());
            dto.setDate(content.getDate());
        }
        return dto;
    }

    public static ContentDTODetail domainToDTODetail(Content content){
        ContentDTODetail dto = null;
        if (content != null){
            dto = new ContentDTODetail();
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
