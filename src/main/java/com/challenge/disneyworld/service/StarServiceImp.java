package com.challenge.disneyworld.service;

import com.challenge.disneyworld.repositories.ContentRepository;
import com.challenge.disneyworld.repositories.StarRepository;
import com.challenge.disneyworld.exceptions.*;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
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

    @Autowired
    private StarRepository starRepository;
    @Autowired
    private ContentRepository contentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StarDTOBase> search(String name, Short age, Float weight, Long movieId){
        return starRepository.search(name, age, weight, movieId).
                stream().
                map(star -> StarMapper.domainToDTOBase(star)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StarDTODetail> getAll() {
        return starRepository.getAll().
                stream().
                map(star -> StarMapper.domainToDTODetail(star)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StarDTODetail getById(Long id){
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star star = starRepository.getById(id);
        if (star == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        return StarMapper.domainToDTODetail(star);
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
    public StarDTODetail updateById(Long id, StarDTODetail dto){
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star starById = starRepository.getById(dto.getId());
        if (starById == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        if (id != dto.getId())
            throw new InvalidDTOException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        Star starByName = starRepository.getByName(dto.getName());
        if (starByName != null && starByName.getId() != starById.getId())
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed.");

        modifyEntityFromDTO(starById, dto);
        return StarMapper.domainToDTODetail(starById);
    }

    @Override
    @Transactional
    public StarDTODetail save(StarDTODetail dto) {
        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        if (starRepository.existsByName(dto.getName()))
            throw new InvalidDTOException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed");

        Star star = new Star();
        modifyEntityFromDTO(star, dto);
        return StarMapper.domainToDTODetail(starRepository.save(star));
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
    public List<ContentDTOBase> getContentsById(Long id) {
        if (id == null){
            throw new InvalidIdException("No id passed");
        }

        Star star = starRepository.getById(id);
        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID.");

        return star.getContents().
                stream().
                map(content -> ContentMapper.domainToDTOBase(content)).
                collect(Collectors.toList());
    }

    private void modifyEntityFromDTO(Star star, StarDTODetail dto){
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
    }
}
