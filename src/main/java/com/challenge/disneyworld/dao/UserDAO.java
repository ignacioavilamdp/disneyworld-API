package com.challenge.disneyworld.dao;

import com.challenge.disneyworld.models.domain.User;

import java.util.List;

/**
 * Interface to interact with an underlying storage mechanism designed to
 * persist User instances.
 */
public interface UserDAO {

    /**
     * Retrieves a user by its name.
     *
     * <p>Precondition: The name must not be null.
     *
     * @param userName the user name.
     * @return the user with the given name or null if the user does not exist
     * in the storage.
     */
    User getByName(String userName);

    /**
     * Retrieves a user by its email.
     *
     * <p>Precondition: The email must not be null.
     *
     * @param email the user email.
     * @return the user with the given email or null if the user does not exist
     * in the storage.
     */
    User getByEmail(String email);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users contained in the storage.
     */
    List<User> getAll();

    /**
     * Saves the given user instance.
     * Returns the user saved as the save operations might have changed the
     * user instance.
     *
     * <p>Precondition: The user must not be null. The user instance must not
     * already exist in the storage. There must not be another user with the
     * same name or email in the storage.
     *
     * <p>Precondition: There must be a transaction when invoked on a container-managed entity
     * manager of type PersistenceContextType.TRANSACTION.
     *
     * <p>Postcondition: The user star instance is persisted into the
     * underlying storage. The returned instance is in a managed state by the
     * persistence context.
     *
     * @param user the user instance to save into the storage.
     * @return the user instance saved into the storage.
     */
    User save(User user);

    /**
     * Returns whether a user with the given name exists.
     *
     * <p>Precondition: The name must not be null.
     *
     * @param userName the user name.
     * @return true if a user with the given name exists in the storage, false
     * otherwise.
     */
    boolean existsByName(String userName);

    /**
     * Returns whether a user with the given email exists.
     *
     * <p>Precondition: The email must not be null.
     *
     * @param email the user name.
     * @return true if a user with the given email exists in the storage, false
     * otherwise.
     */
    boolean existsByEmail(String email);
}
