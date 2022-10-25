package com.challenge.disneyworld.repositories;

import com.challenge.disneyworld.models.domain.Genre;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link GenreRepository} using JPA.
 */
@Component
public class GenreRepositoryImp implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        String string = "SELECT gr FROM Genre gr WHERE gr.name = :name";
        TypedQuery<Genre> query = em.createQuery(string, Genre.class);
        query.setParameter("name", name);
        List<Genre> genres = query.getResultList();
        if (genres.size() != 0){
            return genres.get(0);
        }
        return null;
    }

    @Override
    public List<Genre> getAll() {
        String string = "SELECT gr FROM Genre gr";
        TypedQuery<Genre> query = em.createQuery(string, Genre.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean existsByName(String name) {
        return (getByName(name) != null);
    }
}
