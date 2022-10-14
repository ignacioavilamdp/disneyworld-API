package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;

public interface UserDAO {

    User getByName(String userName);

    boolean existsByUserName(String userName);

}
