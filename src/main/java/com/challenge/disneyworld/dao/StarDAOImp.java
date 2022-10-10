package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.Star;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
public class StarDAOImp implements StarDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Star> search(String name, Short age, String movie){
        String string = "SELECT st FROM Star st " +
                "WHERE  (:age IS NULL OR :age = st.age)" +
                "AND    (:name is NULL OR :name = st.name)";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        query.setParameter("age", age);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Star getById(long id) {
        return em.find(Star.class, id);
    }

    @Override
    @Transactional
    public Star getByName(String name) {
        List<Star> stars = search(name, null, null);
        if (stars.size() != 0){
            return stars.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public List<Star> getAll() {
        String string = "SELECT st FROM Star st";
        TypedQuery<Star> query = em.createQuery(string, Star.class);
        return query.getResultList();
    }


    @Override
    @Transactional
    public Star save(Star star) {
        em.persist(star);
        return star;
    }

    @Override
    @Transactional
    public Star update(Star star) {
        return em.find(Star.class, star.getId()).copy(star);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        em.remove(getById(id));
    }

    @Override
    public boolean isById(long id) {
        return (getById(id) != null);
    }

    @Override
    public boolean isByName(String name) {
        List<Star> stars = search(name, null, null);
        return (stars.size() != 0);
    }


}
