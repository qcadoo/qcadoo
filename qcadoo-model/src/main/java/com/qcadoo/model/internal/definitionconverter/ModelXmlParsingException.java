package com.qcadoo.model.internal.definitionconverter;

@SuppressWarnings("serial")
public class ModelXmlParsingException extends Exception {

    public ModelXmlParsingException(final String message) {
        super(message);
    }

    public ModelXmlParsingException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
