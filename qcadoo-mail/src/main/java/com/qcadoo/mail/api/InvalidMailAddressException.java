package com.qcadoo.mail.api;

import org.springframework.mail.MailParseException;

/**
 * Exception thrown if invalid email address are encountered.
 * 
 * @since 1.1.5
 */
@SuppressWarnings("serial")
public class InvalidMailAddressException extends MailParseException {

    /**
     * Create a new InvalidMailAddressException
     * 
     * @param message
     *            the detail message
     */
    public InvalidMailAddressException(final String message) {
        super(message);
    }
}
