package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.GenreDAO;
import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import com.challenge.disneyworld.models.mappers.GenreMapper;
import com.challenge.disneyworld.models.mappers.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreService {

    @Autowired
    private GenreDAO dao;

    public List<GenreDTODetail> getAllDetail() {
        return dao.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTODetail(genre)).
                collect(Collectors.toList());
    }

    public List<GenreDTOBase> getAllBase() {
        return dao.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTOBase(genre)).
                collect(Collectors.toList());
    }

}

