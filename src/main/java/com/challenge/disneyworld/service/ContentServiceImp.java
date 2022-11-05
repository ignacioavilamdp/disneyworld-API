package com.challenge.disneyworld.service;

import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.domain.Rating;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentDetailDTO;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.GenreRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import com.challenge.disneyworld.exceptions.InvalidIdException;
import com.challenge.disneyworld.exceptions.InvalidOrderCriteriaException;
import com.challenge.disneyworld.exceptions.NonExistentEntityException;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.mappers.ContentMapper;
import com.challenge.disneyworld.models.mappers.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ContentService} using {@link ContentRepository},
 * {@link StarRepository} and {@link GenreRepository} instances.
 */
@Component
public class ContentServiceImp implements ContentService{

    private final ContentRepository contentRepository;
    private final GenreRepository genreRepository;
    private final StarRepository starRepository;
    private final StarService starService;
    private final ContentMapper contentMapper;
    private final StarMapper starMapper;

    @Autowired
    public ContentServiceImp(ContentRepository contentRepository, GenreRepository genreRepository,
                             StarRepository starRepository, StarService starService,
                             ContentMapper contentMapper, StarMapper starMapper) {
        this.contentRepository = contentRepository;
        this.genreRepository = genreRepository;
        this.starRepository = starRepository;
        this.starService = starService;
        this.contentMapper = contentMapper;
        this.starMapper = starMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBaseDTO> search(String title, Integer genreId, String order) {
        if (order != null && !order.equals("ASC") && !order.equals("DESC")){
            throw new InvalidOrderCriteriaException("Invalid order criteria.");
        }
        return contentRepository.search(title, genreId, order).
                stream().
                map(content -> contentMapper.entityToBaseDTO(content)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentDetailDTO> getAll() {
        return contentRepository.getAll().
                stream().
                map(content -> contentMapper.entityToDetailDTO(content)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContentDetailDTO getById(Long id){
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Content content = contentRepository.getById(id);
        if (content == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        return contentMapper.entityToDetailDTO(contentRepository.getById(id));
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Content content = contentRepository.getById(id);
        if (content == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        contentRepository.delete(content);
        return "Movie with ID: " + id + " - successfully removed";
    }

    @Override
    @Transactional
    public ContentDetailDTO updateById(Long id, ContentDetailDTO dto){
        if (id == null)
            throw new InvalidIdException("No id passed");

        Content contentById = contentRepository.getById(id);
        if (contentById == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        if (contentById.getId() != dto.getId())
            throw new InvalidDTOException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getTitle() == null)
            throw new InvalidDTOException("No title passed. Title is mandatory.");

        if (dto.getRating() == null)
            throw new InvalidDTOException("No rating passed. Rating is mandatory.");

        if ( !isValidRating(dto.getRating()) )
            throw new InvalidDTOException("Invalid rating");

        Content contentByDTOTitle = contentRepository.getByTitle(dto.getTitle());
        if (contentByDTOTitle != null && contentByDTOTitle.getId() != contentById.getId())
            throw new InvalidDTOException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        return contentMapper.entityToDetailDTO(
                copyDataFromDTO(contentById, dto));
    }

    @Override
    @Transactional
    public ContentDetailDTO save(ContentDetailDTO dto) {
        if (dto.getTitle() == null)
            throw new InvalidDTOException("No title passed. Title is mandatory.");

        if (dto.getRating() == null)
            throw new InvalidDTOException("No rating passed. Rating is mandatory.");

        if ( !isValidRating(dto.getRating()) )
            throw new InvalidDTOException("Invalid rating");

        if (contentRepository.existsByTitle(dto.getTitle()))
            throw new InvalidDTOException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        return contentMapper.entityToDetailDTO(
                contentRepository.save(
                        copyDataFromDTO(new Content(), dto)));
    }

    @Override
    @Transactional
    public String relateStar(Long starId, Long contentId) {
        return starService.relateContent(starId, contentId);
    }

    @Override
    @Transactional
    public String unRelateStar(Long starId, Long contentId) {
        return starService.unRelateContent(starId, contentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StarBaseDTO> getStarsById(Long id) {
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Content content = contentRepository.getById(id);
        if (content == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        return content.getStars().
                stream().
                map(star -> starMapper.entityToBaseDTO(star)).
                collect(Collectors.toList());
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
