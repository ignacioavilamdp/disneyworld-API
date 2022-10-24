package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link UserDAO} using JPA.
 */
@Component
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAll() {
        String string = "SELECT user FROM User user";
        TypedQuery<User> query = em.createQuery(string, User.class);
        return query.getResultList();
    }

    @Override
    public User getByName(String userName){
        String string = "SELECT user FROM User user WHERE name = :name";
        TypedQuery<User> query = em.createQuery(string, User.class);
        query.setParameter("name", userName);
        List<User> users = query.getResultList();
        if (users.size() != 0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        String string = "SELECT user FROM User user WHERE email = :email";
        TypedQuery<User> query = em.createQuery(string, User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users.size() != 0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public boolean existsByName(String userName){
        return getByName(userName) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return getByEmail(email) != null;
    }

}
