package com.qcadoo.model.internal.search;

import org.hibernate.criterion.Order;

import com.qcadoo.model.api.search.SearchOrder;

public class SearchOrderImpl implements SearchOrder {

    private final Order order;

    public SearchOrderImpl(final Order order) {
        this.order = order;
    }

    @Override
    public Order getHibernateOrder() {
        return order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((order == null) ? 0 : order.toString().hashCode());
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
        if (!(obj instanceof SearchOrderImpl)) {
            return false;
        }
        SearchOrderImpl other = (SearchOrderImpl) obj;
        if (order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!order.toString().equals(other.order.toString())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return order.toString();
    }

}
