package com.qcadoo.security.api;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service for resetting password
 * 
 * @since 1.1.5
 */
public interface PasswordReminderService {

    /**
     * Generate and send via email new password for specified user
     * 
     * @param userName
     *            user name whose password you want to reset
     * @throws UsernameNotFoundException
     *             when user with given name does not exist
     */
    void generateAndSendNewPassword(String userName) throws UsernameNotFoundException;
}
