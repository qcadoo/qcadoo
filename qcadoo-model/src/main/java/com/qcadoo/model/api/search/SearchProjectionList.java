package com.qcadoo.model.api.search;

/**
 * A sequence of {@link SearchProjection}.
 * 
 * @since 0.4.1
 */
public interface SearchProjectionList extends SearchProjection {

    /**
     * Adds projection to this projection's list.
     * 
     * @param projection
     *            projection
     */
    SearchProjectionList add(SearchProjection projection);

}
