package com.challenge.disneyworld.repositories;

import com.challenge.disneyworld.models.domain.Star;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link StarRepository} using JPA.
 */
@Component
public class StarRepositoryImp implements StarRepository {

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
        String string =  "SELECT st FROM Star st WHERE st.name = :name";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        query.setParameter("name", name);
        List<Star> stars = query.getResultList();
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

    @Override
    public boolean existsById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean existsByName(String name) {
        return (getByName(name) != null);
    }

    @Override
    public void delete(Star star) {
        em.remove(star);
    }

}
