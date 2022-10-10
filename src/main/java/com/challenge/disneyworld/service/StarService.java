package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.StarDAO;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.models.mappers.StarMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StarService {

    @Autowired
    private StarDAO dao;

    public List<StarDTOBase> search(String name, Short age, String movie){
        return dao.search(name, age, movie).
                stream().
                map(star -> StarMapper.domainToDTOBase(star)).
                collect(Collectors.toList());
    }

    public List<StarDTODetail> getAll() {
        return dao.getAll().
                stream().
                map(star -> StarMapper.domainToDTODetail(star)).
                collect(Collectors.toList());
    }

    public StarDTODetail getById(Long id){
        return StarMapper.domainToDTODetail(dao.getById(id));
    }

    public StarDTODetail save(StarDTODetail dto) {
        if (dto.getName() == null){
            String message = "Not accepted without name";
            System.out.println(message);
            return null;
        }

        if (dao.isByName(dto.getName())){
            String message = "There is already entity in the db with that name";
            System.out.println(message);
            return null;
        }

        return StarMapper.domainToDTODetail(
                                    dao.save(
                                        StarMapper.DTOToDomain(dto)));
    }

    public StarDTODetail update(StarDTODetail dto){

        if (dto.getName() == null){
            String message = "Not accepted without name";
            System.out.println(message);
            return null;
        }

        Star oldById = dao.getById(dto.getId());
        Star oldByName = dao.getByName(dto.getName());
        if (oldById == null){
            String message = "No entity in the db with that id";
            System.out.println(message);
            return null;
        }

        if (oldByName != null && oldByName.getId() != oldById.getId()){
            String message = "There is already an entity with the same name in the db (different from the entity that must be updated)";
            System.out.println(message);
            return null;
        }

        return StarMapper.domainToDTODetail(
                                    dao.update(
                                        StarMapper.DTOToDomain(dto)));
    }

    public boolean deleteById(Long id) {
        if (!dao.isById(id)){
            String message = "Not entity with that id in the db";
            System.out.println(message);
            return false;
        }

        dao.deleteById(id);
        return (!dao.isById(id));
    }
}
