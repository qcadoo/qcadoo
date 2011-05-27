package com.qcadoo.model.internal.search;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.qcadoo.model.api.search.SearchConjunction;
import com.qcadoo.model.api.search.SearchCriterion;

public class SearchConjunctionImpl implements SearchConjunction {

    private final Conjunction conjunction;

    public SearchConjunctionImpl() {
        conjunction = Restrictions.conjunction();
    }

    @Override
    public Criterion getHibernateCriterion() {
        return conjunction;
    }

    @Override
    public void add(final SearchCriterion criterion) {
        conjunction.add(criterion.getHibernateCriterion());

    }

}
