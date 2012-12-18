package com.qcadoo.report.api;

import java.util.Locale;

/**
 * Resolver for footer.
 * 
 * @author krzysztofnadolski
 * 
 */
public interface FooterResolver {

    /**
     * Resolve footer.
     * 
     * @param locale
     * 
     * @return Footer
     */
    Footer resolveFooter(final Locale locale);

}
