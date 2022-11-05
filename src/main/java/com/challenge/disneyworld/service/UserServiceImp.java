package com.challenge.disneyworld.service;

import com.challenge.disneyworld.models.domain.UserRole;
import com.challenge.disneyworld.repositories.UserRepository;
import com.challenge.disneyworld.models.dto.UserRegisterDTO;
import com.challenge.disneyworld.models.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} using a {@link UserRepository}
 * instance.
 */
@Component
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImp(UserRepository userRepository, UserMapper userMapper,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public String register(UserRegisterDTO dto) {
        userRepository.save(
                userMapper.registerDTOToEntity(dto));

        return "User successfully registered " ;
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String storedName = SecurityContextHolder.getContext().getAuthentication().getName();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        StringBuilder sb = new StringBuilder();
        boolean isUser = false;
        boolean isAdmin = false;
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals(UserRole.ROLE_USER.getAuthority())){
                isUser = true;
            }
            if (grantedAuthority.getAuthority().equals((UserRole.ROLE_ADMIN.getAuthority()))){
                isAdmin = true;
            }
        }

        return "User successfully logged in " + storedName + " isUser: " + isUser + " isAdmin: " + isAdmin;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRegisterDTO> getAll() {
        return userRepository.getAll().
                stream().
                map(user -> userMapper.entityToRegisterDTO(user)).
                collect(Collectors.toList());
    }
}
