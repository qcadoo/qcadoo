package com.qcadoo.model.api.search;

/**
 * A sequence of {@link SearchCriterion} linked using disjunction - (... OR ... OR ...).
 * 
 * @version 0.4.1
 */
public interface SearchDisjunction extends SearchCriterion {

    /**
     * Adds criterion to this disjunction.
     * 
     * @param criterion
     *            criterion
     */
    void add(final SearchCriterion criterion);

}