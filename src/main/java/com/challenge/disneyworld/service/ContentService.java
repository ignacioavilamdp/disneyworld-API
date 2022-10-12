package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.ContentDAO;
import com.challenge.disneyworld.dao.GenreDAO;
import com.challenge.disneyworld.dao.StarDAO;
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
        return ContentMapper.domainToDTODetail(contentDAO.getById(id));
    }

    @Transactional
    public ContentDTODetail save(ContentDTODetail dto) {
        if (dto.getTitle() == null){
            String message = "Not accepted without title";
            System.out.println(message);
            return null;
        }

        if (contentDAO.isByTitle(dto.getTitle())){
            String message = "There is already entity in the db with that title";
            System.out.println(message);
            return null;
        }

        if (dto.getGenreName() == null){
            String message = "Not accepted without genre";
            System.out.println(message);
            return null;
        }

        Genre genre = genreDAO.getByName(dto.getGenreName());
        if (genre == null){
            String message = "No genre with that name";
            System.out.println(message);
            return null;
        }

        return ContentMapper.domainToDTODetail(
                contentDAO.save(
                        ContentMapper.DTOToDomain(dto, genre)));
    }

    @Transactional
    public ContentDTODetail update(ContentDTODetail dto){
        if (dto.getTitle() == null){
            String message = "Not accepted without title";
            System.out.println(message);
            return null;
        }

        Content contentById = contentDAO.getById(dto.getId());
        Content contentByName = contentDAO.getByTitle(dto.getTitle());
        if (contentById == null){
            String message = "No entity in the db with that id";
            System.out.println(message);
            return null;
        }
        if (contentByName != null && contentByName.getId() != contentById.getId()){
            String message = "There is already an entity with the same title in the db (different from the entity that must be updated)";
            System.out.println(message);
            return null;
        }

        if (dto.getGenreName() == null){
            String message = "Not accepted without genre";
            System.out.println(message);
            return null;
        }

        Genre genre = genreDAO.getByName(dto.getGenreName());
        if (genre == null){
            String message = "No genre with that name";
            System.out.println(message);
            return null;
        }

        // Instead of copying, I set each value - To review TODO
        contentById.setTitle(dto.getTitle());
        contentById.setImage(dto.getImage());
        contentById.setDate(dto.getDate());
        contentById.setRating(dto.getRating());
        contentById.setGenre(genre);
        //contentById.setStars(dto.getStars());

        return ContentMapper.domainToDTODetail(contentById);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!contentDAO.isById(id)){
            String message = "Not entity with that id in the db";
            System.out.println(message);
            return false;
        }

        contentDAO.deleteById(id);
        return (!contentDAO.isById(id));
    }

    @Transactional
    public boolean relateStar(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDAO.getById(contentId);

        if (star == null){
            System.out.println("No star with that id");
            return false;
        }

        if (content == null){
            System.out.println("No content with that id");
            return false;
        }

        if (star.getContents().contains(content)){
            System.out.println("The relation already exists");
            return false;
        }

        return star.getContents().add(content);
    }

    @Transactional
    public boolean unRelateStar(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDAO.getById(contentId);

        if (star == null){
            System.out.println("No star with that id");
            return false;
        }

        if (content == null){
            System.out.println("No content with that id");
            return false;
        }

        if (!star.getContents().contains(content)){
            System.out.println("The relation does not exist");
            return false;
        }

        return star.getContents().remove(content);
    }

    @Transactional(readOnly = true)
    public List<StarDTOBase> getStarsById(Long id) {
        if (!contentDAO.isById(id)){
            String message = "Not entity with that id in the db";
            System.out.println(message);
            return null;
        }
        return contentDAO.getStarsById(id).
                stream().
                map(star -> StarMapper.domainToDTOBase(star)).
                collect(Collectors.toList());
    }
}
