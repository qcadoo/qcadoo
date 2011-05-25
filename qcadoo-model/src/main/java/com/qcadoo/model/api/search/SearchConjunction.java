package com.qcadoo.model.api.search;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SearchConjunction implements SearchCriterion {

    private final Conjunction conjunction;

    public SearchConjunction() {
        conjunction = Restrictions.conjunction();
    }

    @Override
    public Criterion getHibernateCriterion() {
        return conjunction;
    }

    public void add(final SearchCriterion criterion) {
        conjunction.add(criterion.getHibernateCriterion());

    }

}
