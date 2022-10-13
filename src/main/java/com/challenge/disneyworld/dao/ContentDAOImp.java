package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ContentDAOImp implements ContentDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Content> search(String title, Integer genreId, Boolean order) {
        String string = "SELECT cnt FROM Content cnt " +
                "WHERE  (:title IS NULL OR :title = cnt.title)" +
                "AND    (:genreId is NULL OR :genreId = cnt.genre.id)";
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
        List<Content> contents = search(title, null, true);
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

    //TODO _ REMOVE, not being used
    @Override
    public Content update(Content content) {
        return em.find(Content.class, content.getId()).copy(content);
    }

    @Override
    public boolean isById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean isByTitle(String title) {
        return (search(title, null, null).size() != 0);
    }

    @Override
    public void delete(Content content) {
        em.remove(content);
    }

    //TODO _ REMOVE, not being used
    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<Star> getStarsById(Long id) {
        String string = "SELECT DISTINCT st FROM Star st " +
                "LEFT OUTER JOIN st.contents content " +
                "WHERE content.id = :id";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


}
