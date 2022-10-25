package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.models.dto.StarDetailDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides static methods to map from {@link Star} to
 * {@link StarBaseDTO} or {@link StarDetailDTO}
 */
public class StarMapper {

    public static StarBaseDTO entityToBaseDTO(Star star){
        StarBaseDTO dto = null;
        if (star != null) {
            dto = new StarBaseDTO();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setImage(star.getImage());
        }
        return dto;
    }

    public static StarDetailDTO entityToDetailDTO(Star star){
        StarDetailDTO dto = null;
        if (star != null) {
            dto = new StarDetailDTO();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setAge(star.getAge());
            dto.setWeight(star.getWeight());
            dto.setImage(star.getImage());
            dto.setHistory(star.getHistory());
            List<String> contentsTitles = new ArrayList<>();
            for (Content content : star.getContents()){
                contentsTitles.add(content.getTitle());
            }
            dto.setContents(contentsTitles);
        }
        return dto;
    }

}
