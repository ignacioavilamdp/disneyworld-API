package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.GenreDAO;
import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;
import com.challenge.disneyworld.models.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreService {

    @Autowired
    private GenreDAO dao;

    @Transactional(readOnly = true)
    public List<GenreDTODetail> getAllDetail() {
        return dao.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTODetail(genre)).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GenreDTOBase> getAllBase() {
        return dao.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTOBase(genre)).
                collect(Collectors.toList());
    }

}

