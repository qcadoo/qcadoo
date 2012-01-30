package com.qcadoo.view.internal.api;

public interface ContextualHelpService {

    /**
     * Lookup context help URL for specified component pattern
     * 
     * @param componentPattern
     *            pattern of component for which you want to find URL
     * @return URL string or null if URL for specified component was not found
     */
    String getHelpUrl(ComponentPattern componentPattern);

    /**
     * Returns contextual help code for given component
     * 
     * @param componentPattern
     *            pattern of component for which you want to get code
     * @return contextual help code or null if showContextualHelpPaths is set to false (@see
     *         {@link ContextualHelpService#isContextualHelpPathsVisible()}) or component is not supported.
     */
    String getContextualHelpKey(ComponentPattern componentPattern);

    /**
     * Lookup context help URL for specified String code
     * 
     * @param code
     *            key for which you want to find URL
     * @return URL string or null if URL for specified code was not found
     */
    String getHelpUrl(String code);

    /**
     * @return showContextualHelpPaths property value
     */
    boolean isContextualHelpPathsVisible();
}
