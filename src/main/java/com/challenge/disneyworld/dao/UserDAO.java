package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    List<User> getAll();
    User getByName(String userName);
    User getByEmail(String email);
    User save(User user);
    boolean existsByName(String userName);
    boolean existsByEmail(String email);
}
