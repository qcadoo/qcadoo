/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
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

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.qcadoo.model.internal.api.DynamicSessionFactoryBean;

public final class DynamicSessionFactoryBeanImpl implements DynamicSessionFactoryBean {

    private final LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

    @Override
    public void afterPropertiesSet() {
        // ignore
    }

    @Override
    public void initialize(final Resource[] hbms) {
        factoryBean.setMappingLocations(hbms);

        try {
            factoryBean.afterPropertiesSet();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
        factoryBean.setBeanClassLoader(classLoader);
    }

    // TODO masz hql security for saas
    // @SuppressWarnings("serial")
    // public static class Listener implements PreDeleteEventListener, PreUpdateEventListener, PreInsertEventListener,
    // PreLoadEventListener {
    //
    // @Override
    // public void onPreLoad(final PreLoadEvent event) {
    // try {
    // Object o = PropertyUtils.getProperty(event.getEntity(), "tenantId");
    //
    // System.out.println(event.getEntity());
    //
    // if (o == null) {
    // return;
    // }
    //
    // System.out.println(o.toString());
    //
    // int i = Integer.valueOf(o.toString());
    // if (i == -1) {
    // throw new SecurityException("Do not touch that!");
    // }
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (InvocationTargetException e) {
    // e.printStackTrace();
    // } catch (NoSuchMethodException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Override
    // public boolean onPreInsert(final PreInsertEvent event) {
    // // TODO Auto-generated method stub
    // return false;
    // }
    //
    // @Override
    // public boolean onPreUpdate(final PreUpdateEvent event) {
    // // TODO Auto-generated method stub
    // return false;
    // }
    //
    // @Override
    // public boolean onPreDelete(final PreDeleteEvent event) {
    // // TODO Auto-generated method stub
    // return false;
    // }
    //
    // }

    @Override
    public SessionFactory getObject() {
        //
        // Listener listener = new Listener();
        //
        // Map<String, Object> eventListeners = new HashMap<String, Object>();
        // eventListeners.put("pre-delete", listener);
        // eventListeners.put("pre-update", listener);
        // eventListeners.put("pre-insert", listener);
        // eventListeners.put("pre-load", listener);
        //
        // factoryBean.setEventListeners(eventListeners);

        return new DynamicSessionFactory(factoryBean);
    }

    @Override
    public Class<?> getObjectType() {
        return factoryBean.getObjectType();
    }

    @Override
    public boolean isSingleton() {
        return factoryBean.isSingleton();
    }

    @Override
    public void destroy() {
        factoryBean.destroy();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        return factoryBean.translateExceptionIfPossible(ex);
    }

    public void setDataSource(final DataSource dataSource) {
        factoryBean.setDataSource(dataSource);
    }

    public void setHibernateProperties(final Properties hibernateProperties) {
        factoryBean.setHibernateProperties(hibernateProperties);
    }

}
