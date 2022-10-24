package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.UserDAO;
import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.domain.User;
import com.challenge.disneyworld.models.domain.UserRole;
import com.challenge.disneyworld.models.dto.UserDTORegister;
import com.challenge.disneyworld.models.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} using a {@link UserDAO}
 * instance.
 */
@Component
public class UserServiceImp implements UserService{

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String register(UserDTORegister dto) {
        if (dto.getName() == null)
            throw new InvalidDTOException("No name passed. Name is mandatory.");

        if (dto.getEmail() == null)
            throw new InvalidDTOException("No email passed. Email is mandatory.");

        if (dto.getPassword() == null)
            throw new InvalidDTOException("No password passed. Password is mandatory.");

        if (dto.getRole() == null)
            throw new InvalidDTOException("No role passed. Role is mandatory.");

        if (userDAO.existsByName(dto.getName()))
            throw new InvalidDTOException("There is already a user with the same name (" + dto.getName() + "). No duplicates allowed");

        if (userDAO.existsByEmail(dto.getEmail()))
            throw new InvalidDTOException("There is already a user with the same email (" + dto.getEmail() + "). No duplicates allowed");

        if ( !isValidRole(dto.getRole())){
            throw new InvalidDTOException("Not a valid user role");
        }

        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(dto.getRole());
        userDAO.save(newUser);
        return "User successfully registered";
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String userName, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User successfully logged in";
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTORegister> getAll() {
        return userDAO.getAll().
                stream().
                map(user -> UserMapper.domainToDTO(user)).
                collect(Collectors.toList());
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
