package com.qcadoo.model.api.search;

import org.hibernate.criterion.Criterion;

public class SearchCriterionImpl implements SearchCriterion {

    private final Criterion criterion;

    public SearchCriterionImpl(final Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public Criterion getHibernateCriterion() {
        return criterion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((criterion == null) ? 0 : criterion.toString().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SearchCriterionImpl)) {
            return false;
        }
        SearchCriterionImpl other = (SearchCriterionImpl) obj;
        if (criterion == null) {
            if (other.criterion != null) {
                return false;
            }
        } else if (!criterion.toString().equals(other.criterion.toString())) {
            return false;
        }
        return true;
    }

}
