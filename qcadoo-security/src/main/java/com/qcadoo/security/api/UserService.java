package com.qcadoo.security.api;

import com.qcadoo.model.api.Entity;

/**
 * This service provides common operations for user
 * 
 * @since 1.2.1
 */
public interface UserService {

    /**
     * Find user by id
     * 
     * @param userId
     *            id of the user to find
     * @return matching user entity or null
     */
    Entity find(final Long userId);

    /**
     * Find user by name
     * 
     * @param userName
     *            name of the user to find
     * @return matching user entity or null
     */
    Entity find(final String userName);

    /**
     * Get current user entity
     * 
     * @return current user entity
     */
    Entity getCurrentUserEntity();

    /**
     * Extract user's first and last names.
     * 
     * @param user
     *            user entity from which full name will be extracted.
     * @return string containing user's first and last names or null if given user is null.
     */
    String extractFullName(final Entity user);

}
