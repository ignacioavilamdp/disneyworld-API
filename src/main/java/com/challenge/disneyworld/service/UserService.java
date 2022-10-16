package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.dto.UserDTORegister;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService{

    List<UserDTORegister> getAll();
    String register(UserDTORegister dto);
    String login(String userName, String password);

}
