package com.qcadoo.maven.plugins;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {

    @Override
    public void error(final SAXParseException e) throws SAXException {
        throw new SAXException(e);
    }

    @Override
    public void fatalError(final SAXParseException e) throws SAXException {
        throw new SAXException(e);
    }

    @Override
    public void warning(final SAXParseException e) throws SAXException {
        throw new SAXException(e);
    }

}
