package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public interface ContentDAO {

    Content getById(long id);
    Content getByTitle(String title);

    List<Content> getAll();
    List<Content> search(String title, Integer genreId, Boolean order);

    Content save(Content content);
    Content update(Content content);

    boolean isById(long id);
    boolean isByTitle(String title);

    void delete(Content content);

    void deleteById(long id);

    List<Star> getStarsById(Long id);
}
