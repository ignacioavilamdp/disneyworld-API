package com.challenge.disneyworld.service;

import com.challenge.disneyworld.exceptions.InvalidDTOException;
import com.challenge.disneyworld.models.dto.UserDTORegister;

import java.util.List;

/**
 * Interface to serve user authentication and authorization purposes.
 */
public interface UserService{

    /**
     * Registers a new user using the data contained in the given dto.
     *
     * <p>Postcondition: A new user is registered.
     *
     * @param dto the dto containing the new user data.
     * @return a message if the registration process was successful.
     */
    String register(UserDTORegister dto);

    /**
     * Logins a user.
     *
     * @param userName the user name.
     * @param password the user password.
     * @return a message if the registration process was successful.
     * @throws InvalidDTOException if some data in the given dto is invalid:
     * the name, email, password or role is null; there is another user with
     * the same name or password; the role is invalid.
     */
    String login(String userName, String password);

    /**
     * Retrieves a list of all registered users.
     *
     * @return a list of all registered users as DTO's.
     */
    List<UserDTORegister> getAll();

}
