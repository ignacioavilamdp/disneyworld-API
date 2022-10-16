package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.ContentDTODetail;
import com.challenge.disneyworld.models.dto.StarDTOBase;

import java.util.List;

public interface ContentService {

    List<ContentDTOBase> search(String title, Integer genreId, Boolean order);
    List<ContentDTODetail> getAll();
    ContentDTODetail getById(Long id);
    ContentDTODetail updateById(Long id, ContentDTODetail dto);
    ContentDTODetail save(ContentDTODetail dto);
    String deleteById(Long id);
    String relateStar(Long starId, Long contentId);
    String unRelateStar(Long starId, Long contentId);
    List<StarDTOBase> getStarsById(Long id);
}
