package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;

import java.util.stream.Collectors;

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
            dto.setGenreName(content.getGenre().getName());
            dto.setStars(
                    content.getStars().
                            stream().
                            map(star -> StarMapper.domainToDTOBase(star)).
                            collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static Content DTOToDomain(ContentDTODetail dto, Genre genre){
        Content content = null;
        if (dto != null){
            content = new Content();
            content.setId(dto.getId());
            content.setTitle(dto.getTitle());
            content.setDate(dto.getDate());
            content.setRating(dto.getRating());
            content.setImage(dto.getImage());
            content.setGenre(genre);
        }
        return content;
    }
}
