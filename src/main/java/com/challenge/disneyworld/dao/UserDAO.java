package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;

import java.util.List;

public interface UserDAO {

    /**
     * Retrieves a user by its name.
     *
     * <p>The name must not be null.
     *
     * @param userName the user name.
     * @return the user with the given name or null if the user does not exist.
     */
    User getByName(String userName);

    /**
     * Retrieves a user by its email.
     *
     * <p>The email must not be null.
     *
     * @param email the user email.
     * @return the user with the given email or null if the user does not exist.
     */
    User getByEmail(String email);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users.
     */
    List<User> getAll();

    /**
     * Saves the given user.
     *
     * <p>The user must not be null and must not be already saved.
     * There must not be another user with the same name or email already saved.
     *
     * <p>There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Returns the user saved as the user operations might have changed the
     * user instance.
     *
     * @param user the user instance to save.
     * @return the user instance saved.
     *
     */
    User save(User user);

    /**
     * Returns whether a user with the given name exists.
     *
     * <p>The name must not be null.
     *
     * @param userName the user name
     * @return true if a user with the given name exists, false otherwise.
     */
    boolean existsByName(String userName);

    /**
     * Returns whether a user with the given email exists.
     *
     * <p>The email must not be null.
     *
     * @param email the user name
     * @return true if a user with the given email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}
