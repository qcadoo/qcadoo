package com.qcadoo.model.api.search;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class SearchDisjunction implements SearchCriterion {

    private final Disjunction disjunction;

    public SearchDisjunction() {
        disjunction = Restrictions.disjunction();
    }

    @Override
    public Criterion getHibernateCriterion() {
        return disjunction;
    }

    public void add(final SearchCriterion criterion) {
        disjunction.add(criterion.getHibernateCriterion());

    }

}
