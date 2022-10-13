package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.ContentDAO;
import com.challenge.disneyworld.dao.GenreDAO;
import com.challenge.disneyworld.dao.StarDAO;
import com.challenge.disneyworld.exceptions.*;
import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Genre;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.mappers.ContentMapper;
import com.challenge.disneyworld.models.mappers.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentService {

    @Autowired
    private ContentDAO contentDAO;

    @Autowired
    private StarDAO starDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private StarService starService;

    @Transactional(readOnly = true)
    public List<ContentDTOBase> search(String title, Integer genreId, Boolean order){
        return contentDAO.search(title, genreId, order).
                stream().
                map(content -> ContentMapper.domainToDTOBase(content)).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContentDTODetail> getAll() {
        return contentDAO.getAll().
                stream().
                map(content -> ContentMapper.domainToDTODetail(content)).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContentDTODetail getById(Long id){
        Content content = contentDAO.getById(id);
        if (content == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        return ContentMapper.domainToDTODetail(contentDAO.getById(id));
    }

    @Transactional
    public String deleteById(Long id) {
        Content content = contentDAO.getById(id);
        if ( content == null )
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        contentDAO.delete(content);
        return "Movie with ID: " + id + " - successfully removed";
    }

    @Transactional
    public ContentDTODetail updateById(Long id, ContentDTODetail dto){
        if (id != dto.getId())
            throw new TryingToModifyIdException("The id in the payload does not match the id in the URI. " +
                    "Are you trying to modify the id? This is not allowed.");

        if (dto.getTitle() == null)
            throw new NoNamePassedException("No title passed. Title is mandatory.");

        Content contentById = contentDAO.getById(dto.getId());
        if (contentById == null)
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        Content contentByTitle = contentDAO.getByTitle(dto.getTitle());
        if (contentByTitle != null && contentByTitle.getId() != contentById.getId())
            throw new DuplicateNameException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        modifyEntityFromDTO(contentById, dto);
        return ContentMapper.domainToDTODetail(contentById);
    }

    @Transactional
    public ContentDTODetail save(ContentDTODetail dto) {
        if (dto.getTitle() == null)
            throw new NoNamePassedException("No title passed. Title is mandatory.");

        if (contentDAO.isByTitle(dto.getTitle()))
            throw new DuplicateNameException("There is already a movie with the same title (" + dto.getTitle() + "). No duplicates allowed");

        // TODO - SOME CHECK OVER ID
        Content content = new Content();
        modifyEntityFromDTO(content, dto);
        return ContentMapper.domainToDTODetail(contentDAO.save(content));
    }

    @Transactional
    public String relateStar(Long starId, Long contentId) {
        return starService.relateContent(starId, contentId);
    }

    @Transactional
    public String unRelateStar(Long starId, Long contentId) {
        return starService.unRelateContent(starId, contentId);
    }

    @Transactional(readOnly = true)
    public List<StarDTOBase> getStarsById(Long id) {
        if (!contentDAO.isById(id))
            throw new NonExistentEntityException("There is no movie with ID: " + id);

        return contentDAO.getStarsById(id).
                stream().
                map(star -> StarMapper.domainToDTOBase(star)).
                collect(Collectors.toList());
    }

    private void modifyEntityFromDTO(Content content, ContentDTODetail dto){
        content.setTitle(dto.getTitle());
        content.setImage(dto.getImage());
        content.setDate(dto.getDate());
        content.setRating(dto.getRating());

        /*
         * First we need to check if the genre entity exists.
         */
        Genre genre = genreDAO.getByName(dto.getGenre());
        if (genre == null)
            throw new NonExistentEntityException("There is no genre named: " + dto.getGenre());
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
            Star star = starDAO.getByName(starName);
            if (star == null)
                throw new NonExistentEntityException("There is no character with name: " + starName + ". Please, add the character first.");
            star.getContents().add(content);
            content.getStars().add(star);
        }
    }
}
