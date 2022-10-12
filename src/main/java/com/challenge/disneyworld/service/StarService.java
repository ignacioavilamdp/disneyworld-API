package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.ContentDAO;
import com.challenge.disneyworld.dao.StarDAO;
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
        return StarMapper.domainToDTODetail(starDAO.getById(id));
    }

    @Transactional
    public StarDTODetail save(StarDTODetail dto) {
        if (dto.getName() == null){
            String message = "Not accepted without name";
            System.out.println(message);
            return null;
        }

        if (starDAO.isByName(dto.getName())){
            String message = "There is already entity in the db with that name";
            System.out.println(message);
            return null;
        }

        return StarMapper.domainToDTODetail(
                                    starDAO.save(
                                        StarMapper.DTOToDomain(dto)));
    }

    @Transactional
    public StarDTODetail update(StarDTODetail dto){
        if (dto.getName() == null){
            String message = "Not accepted without name";
            System.out.println(message);
            return null;
        }
        
        Star starById = starDAO.getById(dto.getId());
        Star starByName = starDAO.getByName(dto.getName());
        if (starById == null){
            String message = "No entity in the db with that id";
            System.out.println(message);
            return null;
        }
        if (starByName != null && starByName.getId() != starById.getId()){
            String message = "There is already an entity with the same name in the db (different from the entity that must be updated)";
            System.out.println(message);
            return null;
        }

        // Instead of copying, I set each value - To review TODO
        //starById.copy(StarMapper.DTOToDomain(dto));
        starById.setName(dto.getName());
        starById.setImage(dto.getImage());
        starById.setAge(dto.getAge());
        starById.setHistory(dto.getHistory());
        starById.setWeight(dto.getWeight());
        //starById.setContents(dto.getContents());

        return StarMapper.domainToDTODetail(starById);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if ( !starDAO.isById(id) ){
            String message = "Not entity with that id in the db";
            System.out.println(message);
            return false;
        }

        return ( starDAO.deleteById(id) );
    }

    @Transactional
    public boolean relateContent(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDao.getById(contentId);

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
    public boolean unRelateContent(Long starId, Long contentId) {
        Star star = starDAO.getById(starId);
        Content content = contentDao.getById(contentId);

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
    public List<ContentDTOBase> getContentsById(Long id) {
        if (!starDAO.isById(id)){
            String message = "Not entity with that id in the db";
            System.out.println(message);
            return null;
        }
        return starDAO.getContentsById(id).
                stream().
                map(content -> ContentMapper.domainToDTOBase(content)).
                collect(Collectors.toList());
    }

}
