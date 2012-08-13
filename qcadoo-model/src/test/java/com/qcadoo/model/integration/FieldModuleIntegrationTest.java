/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.7
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
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.plugin.api.PluginStateResolver;
import com.qcadoo.plugin.internal.PluginUtilsService;

public class FieldModuleIntegrationTest extends IntegrationTest {

    @Test
    public void shouldCallAdditinanalFieldValidators() throws Exception {
        // given
        DataDefinition productDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT);
        DataDefinition machineDao = dataDefinitionService.get(PLUGIN_MACHINES_NAME, ENTITY_NAME_MACHINE);
        DataDefinition componentDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_COMPONENT);

        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled(PLUGIN_MACHINES_NAME)).willReturn(true);

        Entity machine = machineDao.save(createMachine("asd"));

        Entity product = createProduct("asd", "asd");
        product = productDao.save(product);

        Entity component = createComponent("name", product, machine);
        component.setField("machineDescription", "as");

        // when
        component = componentDao.save(component);

        // then
        Assert.assertFalse(component.isValid());
    }

    @Test
    public void shouldCallAndPassAdditinanalFieldValidators() throws Exception {
        // given
        DataDefinition productDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT);
        DataDefinition machineDao = dataDefinitionService.get(PLUGIN_MACHINES_NAME, ENTITY_NAME_MACHINE);
        DataDefinition componentDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_COMPONENT);

        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled(PLUGIN_MACHINES_NAME)).willReturn(true);

        Entity machine = machineDao.save(createMachine("asd"));

        Entity product = createProduct("asd", "asd");
        product = productDao.save(product);

        Entity component = createComponent("name", product, machine);
        component.setField("machineDescription", "asdfghjkl");

        // when
        component = componentDao.save(component);

        // then
        Assert.assertTrue(component.isValid());
    }

    @Test
    public void shouldNotCallAdditinanalFieldValidatorsIfPluginIsSystemDisabled() throws Exception {
        // given
        DataDefinition machineDao = dataDefinitionService.get(PLUGIN_MACHINES_NAME, ENTITY_NAME_MACHINE);
        machineDao.save(createMachine("asd"));

        pluginManager.disablePlugin(PLUGIN_MACHINES_NAME);

        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled(PLUGIN_MACHINES_NAME)).willReturn(false);

        DataDefinition productDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT);
        DataDefinition componentDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_COMPONENT);

        Entity product = createProduct("asd", "asd");
        product = productDao.save(product);

        Entity component = createComponent("name", product, null);
        component.setField("machineDescription", "as");

        // when
        component = componentDao.save(component);

        // then
        Assert.assertTrue(component.isValid());
    }

    @Test
    public void shouldNotCallAdditinanalFieldValidatorsIfPluginIsDisabledForCurrentUser() throws Exception {
        // given
        DataDefinition machineDao = dataDefinitionService.get(PLUGIN_MACHINES_NAME, ENTITY_NAME_MACHINE);
        machineDao.save(createMachine("asd"));

        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled(PLUGIN_MACHINES_NAME)).willReturn(false);

        DataDefinition productDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT);
        DataDefinition componentDao = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_COMPONENT);

        Entity product = createProduct("asd", "asd");
        product = productDao.save(product);

        Entity component = createComponent("name", product, null);
        component.setField("machineDescription", "as");

        // when
        component = componentDao.save(component);

        // then
        Assert.assertTrue(component.isValid());
    }
}
