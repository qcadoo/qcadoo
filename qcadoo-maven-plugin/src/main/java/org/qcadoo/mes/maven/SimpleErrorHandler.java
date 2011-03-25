package org.qcadoo.mes.maven;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("[ERROR] " + exception);
        throw new SAXException();
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("[ERROR] " + exception);
        throw new SAXException();
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("[WARN] " + exception);
        throw new SAXException();
    }

}
