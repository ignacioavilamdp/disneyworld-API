package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.GenreDTOBase;
import com.challenge.disneyworld.models.dto.GenreDTODetail;

import java.util.List;

public interface GenreService {

    List<GenreDTODetail> getAllDetail();
    List<GenreDTOBase> getAllBase();
}
