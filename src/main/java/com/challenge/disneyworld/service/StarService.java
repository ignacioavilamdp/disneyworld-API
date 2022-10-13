package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.ContentDAO;
import com.challenge.disneyworld.dao.StarDAO;
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

@Component
public class StarService {

    @Autowired
    private StarDAO starDAO;
    @Autowired
    private ContentDAO contentDao;

    @Transactional(readOnly = true)
    public List<StarDTOBase> search(String name, Short age, Float weight, Long movieId){
        return starDAO.search(name, age, weight, movieId).
                stream().
                map(star -> StarMapper.domainToDTOBase(star)).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StarDTODetail> getAll() {
        return starDAO.getAll().
                stream().
                map(star -> StarMapper.domainToDTODetail(star)).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StarDTODetail getById(Long id){
        Star star = starDAO.getById(id);
        if (star == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        return StarMapper.domainToDTODetail(star);
    }

    @Transactional
    public String deleteById(Long id) {
        Star star = starDAO.getById(id);
        if ( star == null )
            throw new NonExistentEntityException("There is no character with ID: " + id);

        starDAO.delete(star);
        return "Character with ID: " + id + " - successfully removed";
    }

    @Transactional
    public StarDTODetail updateById(Long id, StarDTODetail dto){
        if (id != dto.getId())
            throw new TryingToModifyIdException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getName() == null)
            throw new NoNamePassedException("No name passed. Name is mandatory.");

        Star starById = starDAO.getById(dto.getId());
        if (starById == null)
            throw new NonExistentEntityException("There is no character with ID: " + id);

        Star starByName = starDAO.getByName(dto.getName());
        if (starByName != null && starByName.getId() != starById.getId())
            throw new DuplicateNameException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed.");

        modifyEntityFromDTO(starById, dto);
        return StarMapper.domainToDTODetail(starById);
    }

    @Transactional
    public StarDTODetail save(StarDTODetail dto) {
        if (dto.getName() == null)
            throw new NoNamePassedException("No name passed. Name is mandatory.");

        if (starDAO.isByName(dto.getName()))
            throw new DuplicateNameException("There is already a character with the same name (" + dto.getName() + "). No duplicates allowed");

        // TODO - SOME CHECK OVER ID
        Star star = new Star();
        modifyEntityFromDTO(star, dto);
        return StarMapper.domainToDTODetail(starDAO.save(star));
    }

    @Transactional
    public String relateContent(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDao.getById(contentId);

        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID: " + starId);

        if (content == null)
            throw new NonExistentEntityException("There is no content with that ID: " + contentId);

        if (star.getContents().contains(content))
            throw new DuplicateRelationException("The character " + starId + " is already related to the movie " + contentId + ".");

        star.getContents().add(content);
        return "Relationship established between character " + starId + " and movie " + contentId;
    }

    @Transactional
    public String unRelateContent(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDao.getById(contentId);

        if (star == null)
            throw new NonExistentEntityException("There is no character with that ID: " + starId);

        if (content == null)
            throw new NonExistentEntityException("There is no content with that ID: " + contentId);

        if (!star.getContents().contains(content))
            throw new NonExistentRelationException("That character " + starId + " is not related to the movie" + contentId + ".");

        star.getContents().remove(content);
        return "Relationship removed between character " + starId + " and movie " + contentId;
    }

    @Transactional(readOnly = true)
    public List<ContentDTOBase> getContentsById(Long id) {
        if (!starDAO.isById(id))
            throw new NonExistentEntityException("There is no character with that ID.");

        return starDAO.getContentsById(id).
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
            Content content = contentDao.getByTitle(contentTitle);
            if (content == null)
                throw new NonExistentEntityException("There is no movie with title: " + contentTitle + ". Please, add the movie first.");
            star.getContents().add(content);
        }
    }
}
