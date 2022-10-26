package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.User;
import com.challenge.disneyworld.models.dto.UserDTORegister;
import org.springframework.stereotype.Component;

/**
 * A class that provides methods to map from {@link User} to
 * {@link UserDTORegister}
 */
@Component
public class UserMapper {

    /**
     * Returns a new {@link UserDTORegister} instance using the data contained
     * in the {@link User} given.
     *
     * @param user the user instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the user
     * instance given, or null if the user is null.
     */
    public UserDTORegister entityToRegisterDTO(User user){
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
