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

    @Autowired
    private StarRepository starRepository;
    @Autowired
    private ContentRepository contentRepository;

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

    /**
     * Returns a new {@link Star} instance using the data contained
     * in the {@link StarDetailDTO} given.
     *
     * <p>Precondition: The dto must not be null.
     *
     * @param dto the dto containing the data to populate a new star instance
     * from.
     * @return a new star instance populated with values extracted from the dto
     * instance given.
     */
    public Star detailDTOToEntity(StarDetailDTO dto){
        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        if (starRepository.existsByName(dto.getName()))
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed");

        return copyDataFromDTO(new Star(), dto);
    }

    /**
     * Returns the same {@link Star} instance given, with its data values
     * updated using the {@link StarDetailDTO} given.
     *
     * <p>Precondition: The star and the dto must not be null.
     *
     * @param star the star instance which data wants to be updated.
     * @param dto the dto instance containing the data to update the star
     * instance from.
     * @return the given star instance populated with values extracted from the
     * dto instance given.
     */
    public Star updateEntityFromDTO(Star star, StarDetailDTO dto){
        if (star.getId() != dto.getId())
            throw new InvalidDTOException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        Star starByDTOName = starRepository.getByName(dto.getName());
        if (starByDTOName != null && starByDTOName.getId() != star.getId())
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed.");

        return copyDataFromDTO(star, dto);
    }

    private Star copyDataFromDTO(Star star, StarDetailDTO dto){
        star.setName(dto.getName());
        star.setImage(dto.getImage());
        star.setAge(dto.getAge());
        star.setHistory(dto.getHistory());
        star.setWeight(dto.getWeight());

        /*
         * We need to remove all the relations between the content and its stars.
         * For each name in the dto we need to check if the content entity exists.
         */
        star.getContents().clear();
        for (String contentTitle : dto.getContents()){
            Content content = contentRepository.getByTitle(contentTitle);
            if (content == null)
                throw new InvalidDTOException("There is no movie with title: " + contentTitle + ". Please, add the movie first.");
            star.getContents().add(content);
        }
        return star;
    }
}
