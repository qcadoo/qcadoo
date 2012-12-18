package com.qcadoo.report.api;

/**
 * Fabric for get appropriate resolver
 * 
 * @author krzysztofnadolski
 * 
 */
public interface FooterResolverFactory {

    /**
     * Get footer resolver
     * 
     * @return {@link FooterResolver}
     */
    FooterResolver getResolver();
}
