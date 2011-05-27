package com.qcadoo.model.api.search;

/**
 * A sequence of {@link SearchCriterion} linked using conjunction - (... AND ... AND ...).
 * 
 * @version 0.4.1
 */
public interface SearchConjunction extends SearchCriterion {

    /**
     * Adds criterion to this conjunction.
     * 
     * @param criterion
     *            criterion
     */
    void add(final SearchCriterion criterion);

}