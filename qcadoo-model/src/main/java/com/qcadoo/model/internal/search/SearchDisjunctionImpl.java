package com.qcadoo.model.internal.search;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.qcadoo.model.api.search.SearchCriterion;
import com.qcadoo.model.api.search.SearchDisjunction;

public class SearchDisjunctionImpl implements SearchDisjunction {

    private final Disjunction disjunction;

    public SearchDisjunctionImpl() {
        disjunction = Restrictions.disjunction();
    }

    @Override
    public Criterion getHibernateCriterion() {
        return disjunction;
    }

    @Override
    public void add(final SearchCriterion criterion) {
        disjunction.add(criterion.getHibernateCriterion());

    }

}
