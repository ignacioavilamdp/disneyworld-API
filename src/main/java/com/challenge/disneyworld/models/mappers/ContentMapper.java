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

    private final ContentRepository contentRepository;
    private final StarRepository starRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public ContentMapper(ContentRepository contentRepository,
                         StarRepository starRepository, GenreRepository genreRepository) {
        this.contentRepository = contentRepository;
        this.starRepository = starRepository;
        this.genreRepository = genreRepository;
    }

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

    /**
     * Returns a new {@link Content} instance using the data contained
     * in the {@link ContentDetailDTO} given.
     *
     * <p>Precondition: The dto must not be null.
     *
     * @param dto the dto containing the data to populate a new content
     * instance from.
     * @return a new content instance populated with values extracted from the
     * dto instance given.
     */
    public Content detailDTOToEntity(ContentDetailDTO dto){
        if (dto.getTitle() == null)
            throw new InvalidDTOException("No title passed. Title is mandatory.");

        if (dto.getRating() == null)
            throw new InvalidDTOException("No rating passed. Rating is mandatory.");

        if ( !isValidRating(dto.getRating()) ){
            throw new InvalidDTOException("Invalid rating");
        }

        if (contentRepository.existsByTitle(dto.getTitle()))
            throw new InvalidDTOException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        return copyDataFromDTO(new Content(), dto);
    }

    /**
     * Returns the same {@link Content} instance given, with its data values
     * updated using the {@link ContentDetailDTO} given.
     *
     * <p>Precondition: The star and the dto must not be null.
     *
     * @param content the content instance which data wants to be updated.
     * @param dto the dto instance containing the data to update the content
     * instance from.
     * @return the given content instance populated with values extracted from the
     * dto instance given.
     */
    public Content updateEntityFromDTO(Content content, ContentDetailDTO dto){
        if (content.getId() != dto.getId())
            throw new InvalidDTOException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getTitle() == null)
            throw new InvalidDTOException("No title passed. Title is mandatory.");

        if (dto.getRating() == null)
            throw new InvalidDTOException("No rating passed. Rating is mandatory.");

        if ( !isValidRating(dto.getRating()) ){
            throw new InvalidDTOException("Invalid rating");
        }

        Content contentByDTOTitle = contentRepository.getByTitle(dto.getTitle());
        if (contentByDTOTitle != null && contentByDTOTitle.getId() != content.getId())
            throw new InvalidDTOException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        return copyDataFromDTO(content, dto);
    }

    private Content copyDataFromDTO(Content content, ContentDetailDTO dto){
        content.setTitle(dto.getTitle());
        content.setImage(dto.getImage());
        content.setDate(dto.getDate());
        content.setRating(Rating.valueOf(dto.getRating()));

        /*
         * First we need to check if the genre entity exists.
         */
        Genre genre = genreRepository.getByName(dto.getGenre());
        if (genre == null)
            throw new InvalidDTOException("There is no genre named: " + dto.getGenre());
        content.setGenre(genre);

        /*
         * We need to remove all the relations between the content and its stars
         * As the star entity is the owning side of the relation, we need to
         * remove the content entity from the star first.
         */
        for (Star star : content.getStars()){
            star.getContents().remove(content);
        }
        content.getStars().clear();

        /*
         * For each name in the dto we need to check if the star entity exists.
         * As the Star entity is the owning side of the relation, we need to
         * add the content entity to the star first.
         */
        for (String starName : dto.getStars()){
            Star star = starRepository.getByName(starName);
            if (star == null)
                throw new InvalidDTOException("There is no character with name: " + starName + ". Please, add the character first.");
            star.getContents().add(content);
            content.getStars().add(star);
        }

        return content;
    }

    private boolean isValidRating(String rating){
        boolean isValid = false;
        for ( Rating validRating : Rating.values() ){
            if (rating.equals(validRating.name())) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
