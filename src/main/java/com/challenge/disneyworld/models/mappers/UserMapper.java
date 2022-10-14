package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.User;
import com.challenge.disneyworld.models.dto.UserDTO;

public class UserMapper {

    public static UserDTO UserDomainToDTO(User user){
        UserDTO dto = null;
        if (user != null){
            dto = new UserDTO();
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
        }
        return dto;
    }
}
