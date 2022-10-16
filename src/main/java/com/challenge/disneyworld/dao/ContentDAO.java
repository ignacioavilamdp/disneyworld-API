package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface ContentDAO {

    Content getById(long id);
    Content getByTitle(String title);
    Content save(Content content);
    void delete(Content content);

    List<Content> getAll();
    List<Content> search(String title, Integer genreId, String order);
    List<Star> getStarsById(Long id);

    boolean existsById(long id);
    boolean existsByTitle(String title);

}
