package com.qcadoo.mail.api;

@SuppressWarnings("serial")
public class MailConfigurationException extends RuntimeException {

    public MailConfigurationException() {
        super();
    }

    public MailConfigurationException(final String message) {
        super(message);
    }

    public MailConfigurationException(final Exception exception) {
        super(exception);
    }

}
