package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.ContentDTOBase;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StarService {

    List<StarDTOBase> search(String name, Short age, Float weight, Long movieId);
    List<StarDTODetail> getAll();
    StarDTODetail getById(Long id);
    StarDTODetail updateById(Long id, StarDTODetail dto);
    StarDTODetail save(StarDTODetail dto);
    String deleteById(Long id);
    String relateContent(Long starId, Long contentId);
    String unRelateContent(Long starId, Long contentId);
    List<ContentDTOBase> getContentsById(Long id);
}
