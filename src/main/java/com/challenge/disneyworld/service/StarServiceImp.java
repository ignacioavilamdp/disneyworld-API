package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.ContentBaseDTO;
import com.challenge.disneyworld.models.dto.StarBaseDTO;
import com.challenge.disneyworld.models.dto.StarDetailDTO;
import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import com.challenge.disneyworld.exceptions.*;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.mappers.ContentMapper;
import com.challenge.disneyworld.models.mappers.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link StarService} using {@link ContentRepository} and
 * {@link StarRepository} instances.
 */
@Component
public class StarServiceImp implements StarService{

    private final StarRepository starRepository;
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final StarMapper starMapper;

    @Autowired
    public StarServiceImp(StarRepository starRepository, ContentRepository contentRepository,
                          ContentMapper contentMapper, StarMapper starMapper) {
        this.starRepository = starRepository;
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.starMapper = starMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StarBaseDTO> search(String name, Short age, Float weight, Long movieId){
        return starRepository.search(name, age, weight, movieId).
                stream().
                map(star -> starMapper.entityToBaseDTO(star)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StarDetailDTO> getAll() {
        return starRepository.getAll().
                stream().
                map(star -> starMapper.entityToDetailDTO(star)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StarDetailDTO getById(Long id){
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star star = starRepository.getById(id);
        if (star == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        return starMapper.entityToDetailDTO(star);
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        Star star = starRepository.getById(id);
        if ( star == null )
            throw new NonExistentEntityException("There is no character with ID: " + id);

        starRepository.delete(star);
        return "Character with ID: " + id + " - successfully removed";
    }

    @Override
    @Transactional
    public StarDetailDTO updateById(Long id, StarDetailDTO dto){
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star starById = starRepository.getById(id);
        if (starById == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        return starMapper.entityToDetailDTO(
                starMapper.updateEntityFromDTO(starById, dto));
    }

    @Override
    @Transactional
    public StarDetailDTO save(StarDetailDTO dto) {
        return starMapper.entityToDetailDTO(
                starRepository.save(
                        starMapper.detailDTOToEntity(dto)));
    }

    @Override
    @Transactional
    public String relateContent(Long starId, Long contentId) {
        if (starId == null){
            throw new InvalidIdException("No character id passed");
        }

        if (contentId == null){
            throw new InvalidIdException("No content id passed");
        }

        Star star = starRepository.getById(starId);
        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID: " + starId);

        Content content = contentRepository.getById(contentId);
        if (content == null)
            throw new NonExistentEntityException("There is no content with that ID: " + contentId);

        if (star.getContents().contains(content))
            throw new DuplicateRelationException("The character " + starId + " is already related to the movie " + contentId + ".");

        star.getContents().add(content);
        return "Relationship established between character " + starId + " and movie " + contentId;
    }

    @Override
    @Transactional
    public String unRelateContent(Long starId, Long contentId) {
        if (starId == null){
            throw new InvalidIdException("No character id passed");
        }

        if (contentId == null){
            throw new InvalidIdException("No content id passed");
        }

        Star star = starRepository.getById(starId);
        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID: " + starId);

        Content content = contentRepository.getById(contentId);
        if (content == null)
            throw new NonExistentEntityException("There is no content with that ID: " + contentId);

        if (!star.getContents().contains(content))
            throw new NonExistentRelationException("That character " + starId + " is not related to the movie" + contentId + ".");

        star.getContents().remove(content);
        return "Relationship removed between character " + starId + " and movie " + contentId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBaseDTO> getContentsById(Long id) {
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star star = starRepository.getById(id);
        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID.");

        return star.getContents().
                stream().
                map(content -> contentMapper.entityToBaseDTO(content)).
                collect(Collectors.toList());
    }


}
