package com.qcadoo.model.internal.api;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;

public interface HibernateService {

    Session getCurrentSession();

    int getTotalNumberOfEntities(Criteria criteria);

    InternalDataDefinition resolveDataDefinition(Criteria criteria);

    InternalDataDefinition resolveDataDefinition(Query query);

    List<?> list(Query query);

    List<?> list(Criteria criteria);

}