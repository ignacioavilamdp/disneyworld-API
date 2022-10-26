package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.ContentDetailDTO;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.GenreRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import com.challenge.disneyworld.exceptions.InvalidIdException;
import com.challenge.disneyworld.exceptions.InvalidOrderCriteriaException;
import com.challenge.disneyworld.exceptions.NonExistentEntityException;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Rating;
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

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private StarRepository starRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private StarService starService;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private StarMapper starMapper;

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
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Content contentById = contentRepository.getById(id);
        if (contentById == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        return contentMapper.entityToDetailDTO(
                contentMapper.updateEntityFromDTO(contentById, dto));
    }

    @Override
    @Transactional
    public ContentDetailDTO save(ContentDetailDTO dto) {
        return contentMapper.entityToDetailDTO(
                contentRepository.save(
                        contentMapper.detailDTOToEntity(dto)));
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

    private boolean isValidRating(String rating){
        boolean isValid = false;
        for ( Rating validRating : Rating.values() ){
            if (rating.equals(validRating.getRate())) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
