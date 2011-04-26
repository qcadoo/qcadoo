package com.qcadoo.model.api.search;

/**
 * Simple implementation of CustomRestriction interface
 * 
 * @since 0.4.0
 */
public class SimpleCustomRestriction implements CustomRestriction {

    private final Restriction restriction;

    /**
     * Basic constructor - defines what restriction should be added
     * 
     * @param restriction
     *            restriction to add
     */
    public SimpleCustomRestriction(final Restriction restriction) {
        this.restriction = restriction;
    }

    @Override
    public void addRestriction(SearchCriteriaBuilder searchCriteriaBuilder) {
        searchCriteriaBuilder.addRestriction(restriction);
    }

}
