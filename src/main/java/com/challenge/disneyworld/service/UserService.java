package com.challenge.disneyworld.service;

import com.challenge.disneyworld.dao.UserDAO;
import com.challenge.disneyworld.exceptions.DuplicateNameException;
import com.challenge.disneyworld.exceptions.NoNamePassedException;
import com.challenge.disneyworld.models.domain.User;
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

@Component
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public String register(UserDTORegister dto) {
        if (dto.getName() == null)
            throw new NoNamePassedException("No name passed. Name is mandatory.");

        if (dto.getEmail() == null)
            throw new NoNamePassedException("No email passed. Email is mandatory.");

        if (dto.getPassword() == null)
            throw new NoNamePassedException("No password passed. Password is mandatory.");

        if (userDAO.existsByName(dto.getName()))
            throw new DuplicateNameException("There is already a user with the same name (" + dto.getName() + "). No duplicates allowed");

        if (userDAO.existsByEmail(dto.getEmail()))
            throw new DuplicateNameException("There is already a user with the same email (" + dto.getEmail() + "). No duplicates allowed");

        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        userDAO.save(newUser);
        return "User successfully registered";
    }

    @Transactional(readOnly = true)
    public String login(String userName, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User successfully logged in";
    }

    @Transactional(readOnly = true)
    public List<UserDTORegister> getAll() {
        return userDAO.getAll().
                stream().
                map(user -> UserMapper.userDomainToDTO(user)).
                collect(Collectors.toList());
    }
}
