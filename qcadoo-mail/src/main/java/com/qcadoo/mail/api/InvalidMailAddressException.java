package com.qcadoo.mail.api;

@SuppressWarnings("serial")
public class InvalidMailAddressException extends RuntimeException {

    public InvalidMailAddressException() {
        super();
    }

    public InvalidMailAddressException(final String message) {
        super(message);
    }
}
