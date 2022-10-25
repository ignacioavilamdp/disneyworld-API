package com.challenge.disneyworld.service;

import com.challenge.disneyworld.repositories.GenreRepository;
import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;
import com.challenge.disneyworld.models.mappers.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreServiceImp implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDTODetail> getAllDetail() {
        return genreRepository.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTODetail(genre)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDTOBase> getAllBase() {
        return genreRepository.getAll().
                stream().
                map(genre -> GenreMapper.domainToDTOBase(genre)).
                collect(Collectors.toList());
    }

}

