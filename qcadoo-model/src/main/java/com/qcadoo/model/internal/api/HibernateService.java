package com.qcadoo.model.internal.api;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;


public interface HibernateService {

    public abstract Session getCurrentSession();

    public abstract int getTotalNumberOfEntities(final Criteria criteria);

    public abstract InternalDataDefinition resolveDataDefinition(final Criteria criteria);

    public abstract InternalDataDefinition resolveDataDefinition(final Query query);

}