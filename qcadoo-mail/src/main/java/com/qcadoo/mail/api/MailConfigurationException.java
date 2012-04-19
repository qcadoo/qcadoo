package com.qcadoo.mail.api;

import org.springframework.mail.MailException;

/**
 * Exception thrown when a mail configuration error is encountered.
 * 
 * @since 1.1.5
 */
@SuppressWarnings("serial")
public class MailConfigurationException extends MailException {

    /**
     * Create a new MailConfigurationException
     * 
     * @param message
     *            the detail message
     */
    public MailConfigurationException(final String message) {
        super(message);
    }

    /**
     * Create a new MailConfigurationException
     * 
     * @param message
     *            the detail message
     * @param cause
     *            the root cause
     */
    public MailConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
