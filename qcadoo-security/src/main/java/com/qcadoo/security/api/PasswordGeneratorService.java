package com.qcadoo.security.api;

/**
 * Service for generating passwords
 * 
 */
public interface PasswordGeneratorService {

    /**
     * Default password length
     */
    int DEFAULT_PASSWORD_LENGTH = 10;

    /**
     * Generate random password with default length {@link PasswordGeneratorService#DEFAULT_PASSWORD_LENGTH}
     * 
     * @return generated random password
     */
    String generatePassword();

    /**
     * Generate random password with specified length
     * 
     * @param length
     *            expected password length
     * 
     * @return generated random password
     */
    String generatePassword(int length);
}
