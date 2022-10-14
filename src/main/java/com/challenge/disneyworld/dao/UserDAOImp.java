package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager em;

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
    public boolean existsByUserName(String userName){
        return getByName(userName) != null;
    }

}
