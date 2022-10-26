package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.mappers.ContentMapper;
import com.challenge.disneyworld.repositories.GenreRepository;
import com.challenge.disneyworld.models.dto.GenreBaseDTO;
import com.challenge.disneyworld.models.dto.GenreDetailDTO;
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
    @Autowired
    private GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDetailDTO> getAllDetail() {
        return genreRepository.getAll().
                stream().
                map(genre -> genreMapper.entityToDetailDTO(genre)).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreBaseDTO> getAllBase() {
        return genreRepository.getAll().
                stream().
                map(genre -> genreMapper.entityToBaseDTO(genre)).
                collect(Collectors.toList());
    }

}

