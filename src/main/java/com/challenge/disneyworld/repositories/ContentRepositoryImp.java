package com.challenge.disneyworld.repositories;

import com.challenge.disneyworld.models.domain.Content;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link ContentRepository} using JPA.
 */
@Component
public class ContentRepositoryImp implements ContentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Content> search(String title, Integer genreId, String order) {
        String string = "SELECT cnt FROM Content cnt " +
                "WHERE  (:title IS NULL OR :title = cnt.title) " +
                "AND    (:genreId is NULL OR :genreId = cnt.genre.id) " +
                "ORDER BY cnt.title ";
        if (order != null)
            string += order;
        TypedQuery<Content> query = em.createQuery(string, Content.class);
        query.setParameter("title", title);
        query.setParameter("genreId", genreId);
        return query.getResultList();
    }

    @Override
    public List<Content> getAll() {
        String string = "SELECT cnt FROM Content cnt";
        TypedQuery<Content> query = em.createQuery(string, Content.class);
        return query.getResultList();
    }

    @Override
    public Content getById(long id) {
        return em.find(Content.class, id);
    }

    @Override
    public Content getByTitle(String title) {
        String string =  "SELECT cnt FROM Content cnt WHERE cnt.title = :title";
        TypedQuery<Content> query = em.createQuery(string, Content.class);
        query.setParameter("title", title);
        List<Content> contents = query.getResultList();
        if (contents.size() != 0){
            return contents.get(0);
        }
        return null;
    }

    @Override
    public Content save(Content content) {
        em.persist(content);
        return content;
    }

    @Override
    public boolean existsById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean existsByTitle(String title) {
        return (getByTitle(title) != null);
    }

    @Override
    public void delete(Content content) {
        em.remove(content);
    }

}
