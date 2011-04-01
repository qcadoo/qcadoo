/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */

package com.qcadoo.model.internal.sessionfactory;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.FactoryBean;

public class DynamicSessionFactory implements SessionFactory {

    private static final long serialVersionUID = -7254335636932770807L;

    private final FactoryBean<SessionFactory> sessionFactoryBean;

    private volatile SessionFactory sessionFactory;

    public DynamicSessionFactory(final FactoryBean<SessionFactory> sessionFactoryBean) {
        this.sessionFactoryBean = sessionFactoryBean;
    }

    private SessionFactory getSessionFactory() {
        SessionFactory result = sessionFactory;
        if (result == null) {
            synchronized (this) {
                result = sessionFactory;
                if (result == null) {
                    try {
                        result = sessionFactory = sessionFactoryBean.getObject();
                    } catch (Exception e) {
                        throw new IllegalStateException(e.getMessage(), e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Reference getReference() throws NamingException {
        return getSessionFactory().getReference();
    }

    @Override
    public Session openSession() {
        return getSessionFactory().openSession();
    }

    @Override
    public Session openSession(final Interceptor interceptor) {
        return getSessionFactory().openSession(interceptor);
    }

    @Override
    public Session openSession(final Connection connection) {
        return getSessionFactory().openSession(connection);
    }

    @Override
    public Session openSession(final Connection connection, final Interceptor interceptor) {
        return getSessionFactory().openSession(connection, interceptor);
    }

    @Override
    public Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return getSessionFactory().openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(final Connection connection) {
        return getSessionFactory().openStatelessSession(connection);
    }

    @Override
    public ClassMetadata getClassMetadata(final Class entityClass) {
        return getSessionFactory().getClassMetadata(entityClass);
    }

    @Override
    public ClassMetadata getClassMetadata(final String entityName) {
        return getSessionFactory().getClassMetadata(entityName);
    }

    @Override
    public CollectionMetadata getCollectionMetadata(final String roleName) {
        return getSessionFactory().getCollectionMetadata(roleName);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return getSessionFactory().getAllClassMetadata();
    }

    @Override
    public Map getAllCollectionMetadata() {
        return getSessionFactory().getAllCollectionMetadata();
    }

    @Override
    public Statistics getStatistics() {
        return getSessionFactory().getStatistics();
    }

    @Override
    public void close() {
        getSessionFactory().close();
    }

    @Override
    public boolean isClosed() {
        return getSessionFactory().isClosed();
    }

    @Override
    public Cache getCache() {
        return getSessionFactory().getCache();
    }

    @Override
    public void evict(final Class persistentClass) {
        getSessionFactory().evict(persistentClass);
    }

    @Override
    public void evict(final Class persistentClass, final Serializable id) {
        getSessionFactory().evict(persistentClass, id);
    }

    @Override
    public void evictEntity(final String entityName) {
        getSessionFactory().evictEntity(entityName);
    }

    @Override
    public void evictEntity(final String entityName, final Serializable id) {
        getSessionFactory().evictEntity(entityName, id);
    }

    @Override
    public void evictCollection(final String roleName) {
        getSessionFactory().evictCollection(roleName);
    }

    @Override
    public void evictCollection(final String roleName, final Serializable id) {
        getSessionFactory().evictCollection(roleName, id);
    }

    @Override
    public void evictQueries(final String cacheRegion) {
        getSessionFactory().evictQueries(cacheRegion);
    }

    @Override
    public void evictQueries() {
        getSessionFactory().evictQueries();
    }

    @Override
    public Set getDefinedFilterNames() {
        return getSessionFactory().getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(final String filterName) {
        return getSessionFactory().getFilterDefinition(filterName);
    }

    @Override
    public boolean containsFetchProfileDefinition(final String name) {
        return getSessionFactory().containsFetchProfileDefinition(name);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return getSessionFactory().getTypeHelper();
    }

}
