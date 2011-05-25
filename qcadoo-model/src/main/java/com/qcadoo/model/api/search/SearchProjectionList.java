package com.qcadoo.model.api.search;

/**
 * @since 0.4.1
 */
public interface SearchProjectionList extends SearchProjection {

    SearchProjectionList add(SearchProjection projection);

}
