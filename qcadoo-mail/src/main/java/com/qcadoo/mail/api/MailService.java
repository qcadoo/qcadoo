package com.qcadoo.mail.api;

/**
 * Service for sending email.
 * 
 * @since 1.1.5
 */
public interface MailService {

    /**
     * Send plain-text mail using default (specified in mail.username property) sender e-mail.
     * 
     * @param recipient
     *            e-mail recipient
     * @param subject
     *            e-mail subject
     * @param body
     *            e-mail body
     * 
     * @throws IllegalArgumentException
     *             if given recipient is not valid e-mail address or any argument is blank
     * @throws MailConfigurationException
     *             if mail.properties is not valid
     * @throws InvalidMailAddressException
     *             if one of email address is blank or invalid
     */
    void sendPlainTextEmail(String recipient, String subject, String body) throws IllegalArgumentException,
            MailConfigurationException, InvalidMailAddressException;
}
