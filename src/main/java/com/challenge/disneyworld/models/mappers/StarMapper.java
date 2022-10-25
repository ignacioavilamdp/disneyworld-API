package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides static methods to map from {@link Star} to
 * {@link StarDTOBase} or {@link StarDTODetail}
 */
public class StarMapper {

    public static StarDTOBase domainToDTOBase(Star star){
        StarDTOBase dto = null;
        if (star != null) {
            dto = new StarDTOBase();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setImage(star.getImage());
        }
        return dto;
    }

    public static StarDTODetail domainToDTODetail(Star star){
        StarDTODetail dto = null;
        if (star != null) {
            dto = new StarDTODetail();
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
