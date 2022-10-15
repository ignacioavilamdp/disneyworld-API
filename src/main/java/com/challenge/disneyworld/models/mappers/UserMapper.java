package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.User;
import com.challenge.disneyworld.models.dto.UserDTORegister;

public class UserMapper {

    public static UserDTORegister userDomainToDTO(User user){
        UserDTORegister dto = null;
        if (user != null){
            dto = new UserDTORegister();
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setRole(user.getRole());
        }
        return dto;
    }
}
