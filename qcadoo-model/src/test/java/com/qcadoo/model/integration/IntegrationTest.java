/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.6
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
package com.qcadoo.model.integration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.InternalDataDefinitionService;
import com.qcadoo.plugin.api.PluginManager;
import com.qcadoo.plugin.api.PluginStateResolver;
import com.qcadoo.plugin.internal.PluginUtilsService;
import com.qcadoo.tenant.api.MultiTenantUtil;
import com.qcadoo.tenant.internal.DefaultMultiTenantService;

public abstract class IntegrationTest {

    protected static String PLUGIN_PRODUCTS_NAME = "products";

    protected static String PLUGIN_MACHINES_NAME = "machines";

    protected static String ENTITY_NAME_PRODUCT = "product";

    protected static String ENTITY_NAME_PART = "part";

    protected static String ENTITY_NAME_MACHINE = "machine";

    protected static String ENTITY_NAME_COMPONENT = "component";

    protected static String TABLE_NAME_PRODUCT = PLUGIN_PRODUCTS_NAME + "_" + ENTITY_NAME_PRODUCT;

    protected static String TABLE_NAME_MACHINE = PLUGIN_MACHINES_NAME + "_" + ENTITY_NAME_MACHINE;

    protected static String TABLE_NAME_COMPONENT = PLUGIN_PRODUCTS_NAME + "_" + ENTITY_NAME_COMPONENT;

    protected static String TABLE_NAME_PART = PLUGIN_PRODUCTS_NAME + "_" + ENTITY_NAME_PART;

    protected static InternalDataDefinitionService dataDefinitionService;

    protected static SessionFactory sessionFactory;

    protected static JdbcTemplate jdbcTemplate;

    protected static ClassPathXmlApplicationContext applicationContext;

    @BeforeClass
    public static void classInit() throws Exception {
        MultiTenantUtil multiTenantUtil = new MultiTenantUtil();
        ReflectionTestUtils.setField(multiTenantUtil, "multiTenantService", new DefaultMultiTenantService());
        multiTenantUtil.init();

        PluginStateResolver mockPluginStateResolver = mock(PluginStateResolver.class);
        given(mockPluginStateResolver.isEnabled(Mockito.anyString())).willReturn(true);

        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", mockPluginStateResolver);
        pluginUtil.init();

        applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("standalone");
        applicationContext.setConfigLocation("spring.xml");
        applicationContext.refresh();
        dataDefinitionService = applicationContext.getBean(InternalDataDefinitionService.class);
        sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
        jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
    }

    @Before
    public void init() throws Exception {
        jdbcTemplate.execute("delete from " + TABLE_NAME_PART);
        jdbcTemplate.execute("delete from " + TABLE_NAME_COMPONENT);
        jdbcTemplate.execute("delete from " + TABLE_NAME_MACHINE);
        jdbcTemplate.execute("delete from " + TABLE_NAME_PRODUCT);
        applicationContext.getBean(PluginManager.class).enablePlugin("machines");
    }

    protected Entity createComponent(final String name, final Object product, final Object machine) {
        Entity entity = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_COMPONENT).create();
        entity.setField("name", name);
        entity.setField("product", product);
        entity.setField("machine", machine);
        return entity;
    }

    protected Entity createPart(final String name, final Object product) {
        Entity entity = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PART).create();
        entity.setField("name", name);
        entity.setField("product", product);
        return entity;
    }

    protected Entity createMachine(final String name) {
        Entity entity = dataDefinitionService.get(PLUGIN_MACHINES_NAME, ENTITY_NAME_MACHINE).create();
        entity.setField("name", name);
        return entity;
    }

    protected Entity createProduct(final String name, final String number) {
        Entity entity = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT).create();
        entity.setField("name", name);
        entity.setField("number", number);
        return entity;
    }

}
