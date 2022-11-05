package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.domain.User;
import com.challenge.disneyworld.models.domain.UserRole;
import com.challenge.disneyworld.models.dto.UserRegisterDTO;
import com.challenge.disneyworld.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A class that provides methods to map from {@link User} to
 * {@link UserRegisterDTO}
 */
@Component
public class UserMapper {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Returns a new {@link UserRegisterDTO} instance using the data contained
     * in the {@link User} given.
     *
     * @param user the user instance to create a DTO from.
     * @return a new DTO instance populated with values extracted from the user
     * instance given, or null if the user is null.
     */
    public UserRegisterDTO entityToRegisterDTO(User user){
        UserRegisterDTO dto = null;
        if (user != null){
            dto = new UserRegisterDTO();
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setRole(user.getRole());
        }
        return dto;
    }

    /**
     * Returns a new {@link User} instance using the data contained
     * in the {@link UserRegisterDTO} given.
     *
     * <p>Precondition: The dto must not be null.
     *
     * @param dto the dto containing the data to populate a new user
     * instance from.
     * @return a new user instance populated with values extracted from the
     * dto instance given.
     */
    public User registerDTOToEntity(UserRegisterDTO dto){
        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        if (dto.getEmail() == null)
            throw new InvalidDTOException("No email passed. Email is mandatory.");

        if (dto.getPassword() == null)
            throw new InvalidDTOException("No password passed. Password is mandatory.");

        if (dto.getRole() == null)
            throw new InvalidDTOException("No role passed. Role is mandatory.");

        if (userRepository.existsByName(dto.getName()))
            throw new InvalidDTOException("There is already a user with the same name (" + dto.getName() + "). No duplicates allowed");

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new InvalidDTOException("There is already a user with the same email (" + dto.getEmail() + "). No duplicates allowed");

        if ( !isValidRole(dto.getRole())){
            throw new InvalidDTOException("Not a valid user role");
        }

        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(dto.getRole());

        return newUser;
    }

    private boolean isValidRole(String role) {
        boolean isValid = false;
        for (UserRole validRole : UserRole.values()){
            if ( role.equals(validRole.getAuthority()) ) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
