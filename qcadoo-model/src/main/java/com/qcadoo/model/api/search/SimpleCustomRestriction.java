package com.qcadoo.model.api.search;

public class SimpleCustomRestriction implements CustomRestriction {

    private final Restriction restriction;

    public SimpleCustomRestriction(final Restriction restriction) {
        this.restriction = restriction;
    }

    @Override
    public void addRestriction(SearchCriteriaBuilder searchCriteriaBuilder) {
        searchCriteriaBuilder.addRestriction(restriction);
    }

}
