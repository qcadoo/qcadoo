package com.qcadoo.model.internal.search;

import org.hibernate.criterion.Projection;

import com.qcadoo.model.api.search.SearchProjection;

public class SearchProjectionImpl implements SearchProjection {

    private final Projection projection;

    public SearchProjectionImpl(final Projection projection) {
        this.projection = projection;
    }

    @Override
    public Projection getHibernateProjection() {
        return projection;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projection == null) ? 0 : projection.toString().hashCode());
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
        if (!(obj instanceof SearchProjectionImpl)) {
            return false;
        }
        SearchProjectionImpl other = (SearchProjectionImpl) obj;
        if (projection == null) {
            if (other.projection != null) {
                return false;
            }
        } else if (!projection.toString().equals(other.projection.toString())) {
            return false;
        }
        return true;
    }

}
