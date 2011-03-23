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

package com.qcadoo.plugin.internal.dao;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.qcadoo.model.beans.plugins.PluginsPlugin;
import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.internal.api.PluginDao;

@Service
public class DefaultPluginDao implements PluginDao {

    @Autowired
    @Qualifier("plugin")
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(final PluginsPlugin plugin) {
        sessionFactory.getCurrentSession().save(plugin);
    }

    @Override
    @Transactional
    public void save(final Plugin plugin) {
        PluginsPlugin existingPlugin = get(plugin.getIdentifier());
        if (existingPlugin != null) {
            existingPlugin.setState(plugin.getState().toString());
            existingPlugin.setVersion(plugin.getVersion().toString());
        } else {
            existingPlugin = new PluginsPlugin(plugin);
        }
        save(existingPlugin);
    }

    @Override
    @Transactional
    public void delete(final PluginsPlugin plugin) {
        sessionFactory.getCurrentSession().delete(plugin);
    }

    @Override
    @Transactional
    public void delete(final Plugin plugin) {
        PluginsPlugin existingPlugin = get(plugin.getIdentifier());
        if (existingPlugin != null) {
            delete(existingPlugin);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Set<PluginsPlugin> list() {
        return Sets.newHashSet(sessionFactory.getCurrentSession().createCriteria(PluginsPlugin.class).list());
    }

    private PluginsPlugin get(final String identifier) {
        return (PluginsPlugin) sessionFactory.getCurrentSession().createCriteria(PluginsPlugin.class)
                .add(Restrictions.eq("identifier", identifier)).uniqueResult();
    }

    void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
