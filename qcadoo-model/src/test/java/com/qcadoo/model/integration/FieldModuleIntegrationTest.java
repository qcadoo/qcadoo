package com.qcadoo.model.integration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.plugin.api.PluginManager;
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

        applicationContext.getBean(PluginManager.class).disablePlugin(PLUGIN_MACHINES_NAME);

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
