package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.models.dto.StarDetailDTO;
import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides static methods to map between {@link Star} and
 * {@link StarBaseDTO} or {@link StarDetailDTO}
 */
@Component
public class StarMapper {

    /**
     * Returns a new {@link StarBaseDTO} instance using the data contained
     * in the {@link Star} given.
     *
     * @param star the star instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the star
     * instance given, or null if the star is null.
     */
    public StarBaseDTO entityToBaseDTO(Star star){
        StarBaseDTO dto = null;
        if (star != null) {
            dto = new StarBaseDTO();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setImage(star.getImage());
        }
        return dto;
    }

    /**
     * Returns a new {@link StarDetailDTO} instance using the data contained
     * in the {@link Star} given.
     *
     * @param star the star instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the star
     * instance given, or null if the star is null.
     */
    public StarDetailDTO entityToDetailDTO(Star star){
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
