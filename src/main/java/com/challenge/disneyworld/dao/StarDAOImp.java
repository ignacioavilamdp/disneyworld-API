package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class StarDAOImp implements StarDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Star> search(String name, Short age, Float weight, Long movieId){
        String string =
                "SELECT DISTINCT st FROM Star st " +
                "LEFT OUTER JOIN st.contents as content " +
                "WHERE  (:age IS NULL OR :age = st.age) " +
                "AND    (:name is NULL OR :name = st.name) " +
                "AND    (:weight is NULL OR :weight = st.weight) " +
                "AND    (:movieId is NULL OR :movieId = content.id)";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        query.setParameter("age", age);
        query.setParameter("name", name);
        query.setParameter("weight", weight);
        query.setParameter("movieId", movieId);
        return query.getResultList();
    }

    @Override
    public List<Star> getAll() {
        String string = "SELECT st FROM Star st";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        return query.getResultList();
    }

    @Override
    public Star getById(long id) {
        return em.find(Star.class, id);
    }

    @Override
    public Star getByName(String name) {
        List<Star> stars = search(name, null, null, null);
        if (stars.size() != 0){
            return stars.get(0);
        }
        return null;
    }

    @Override
    public Star save(Star star) {
        em.persist(star);
        return star;
    }

    //TODO _ REMOVE, not being used
    public Star update(Star star) {
        return em.find(Star.class, star.getId()).copy(star);
    }

    @Override
    public boolean isById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean isByName(String name) {
        return (search(name, null, null,null).size() != 0);
    }

    @Override
    public void delete(Star star) {
        em.remove(star);
    }

    //TODO _ REMOVE, not being used
    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<Content> getContentsById(Long id) {
        String string = "SELECT DISTINCT cnt FROM Content cnt " +
                "LEFT OUTER JOIN cnt.stars star " +
                "WHERE star.id = :id";
        TypedQuery<Content> query = em.createQuery(string, Content.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

}
