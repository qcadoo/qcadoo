package com.qcadoo.model.internal.search;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.search.SearchQueryBuilder;

public interface SearchQuery extends SearchQueryBuilder {

    Query createQuery(Session session);

    DataDefinition getDataDefinition();

    void addParameters(Query query);

    void addFirstAndMaxResults(Query query);

}
