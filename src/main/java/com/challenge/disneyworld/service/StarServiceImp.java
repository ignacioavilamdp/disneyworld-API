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
        if (id == null)
            throw new InvalidIdException("No id passed");

        Star starById = starRepository.getById(id);
        if (starById == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        if (starById.getId() != dto.getId())
            throw new InvalidDTOException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        Star starByDTOName = starRepository.getByName(dto.getName());
        if (starByDTOName != null && starByDTOName.getId() != starById.getId())
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed.");

        return starMapper.entityToDetailDTO(
                copyDataFromDTO(starById, dto));
    }

    @Override
    @Transactional
    public StarDetailDTO save(StarDetailDTO dto) {
        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        if (starRepository.existsByName(dto.getName()))
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed");

        return starMapper.entityToDetailDTO(
                starRepository.save(
                        copyDataFromDTO(new Star(), dto)));
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
