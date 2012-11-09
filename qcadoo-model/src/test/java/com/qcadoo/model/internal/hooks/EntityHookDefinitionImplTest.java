/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0-SNAPSHOT
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
package com.qcadoo.model.internal.hooks;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.beans.sample.CustomEntityService;
import com.qcadoo.plugin.api.PluginStateResolver;
import com.qcadoo.plugin.internal.PluginUtilsService;

public class EntityHookDefinitionImplTest {

    private EntityHookDefinitionImpl entityHookDefinitionImpl;

    @Before
    public final void init() throws HookInitializationException {
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        BDDMockito.given(applicationContext.getBean(CustomEntityService.class)).willReturn(new CustomEntityService());
        entityHookDefinitionImpl = new EntityHookDefinitionImpl("com.qcadoo.model.beans.sample.CustomEntityService", "onSave",
                "somePlugin", applicationContext);
    }

    @Test
    public final void shouldIsEnabledReturnTrueIfSourcePluginIsEnabledForCurrentUser() throws Exception {
        // given
        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled("somePlugin")).willReturn(true);
        given(pluginStateResolver.isEnabledOrEnabling("somePlugin")).willReturn(true);

        entityHookDefinitionImpl.enable();

        // when
        boolean isEnabled = entityHookDefinitionImpl.isEnabled();

        // then
        Assert.assertTrue(isEnabled);
    }

    @Test
    public final void shouldIsEnabledReturnFalseIfSourcePluginIsEnabledForCurrentUserButDisableInSystem() throws Exception {
        // given
        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled("somePlugin")).willReturn(true);
        given(pluginStateResolver.isEnabledOrEnabling("somePlugin")).willReturn(true);

        entityHookDefinitionImpl.disable();

        // when
        boolean isEnabled = entityHookDefinitionImpl.isEnabled();

        // then
        Assert.assertFalse(isEnabled);
    }

    @Test
    public final void shouldIsEnabledReturnFalseIfSourcePluginIsDisabled() throws Exception {
        // given
        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled("somePlugin")).willReturn(false);
        given(pluginStateResolver.isEnabledOrEnabling("somePlugin")).willReturn(false);

        entityHookDefinitionImpl.disable();

        // when
        boolean isEnabled = entityHookDefinitionImpl.isEnabled();

        // then
        Assert.assertFalse(isEnabled);
    }

    @Test
    public final void shouldIsEnabledReturnFalseIfSourcePluginIsDisabledOnlyForCurrentUser() throws Exception {
        // given
        PluginStateResolver pluginStateResolver = mock(PluginStateResolver.class);
        PluginUtilsService pluginUtil = new PluginUtilsService();
        ReflectionTestUtils.setField(pluginUtil, "pluginStateResolver", pluginStateResolver);
        pluginUtil.init();
        given(pluginStateResolver.isEnabled("somePlugin")).willReturn(false);
        given(pluginStateResolver.isEnabledOrEnabling("somePlugin")).willReturn(false);

        entityHookDefinitionImpl.enable();

        // when
        boolean isEnabled = entityHookDefinitionImpl.isEnabled();

        // then
        Assert.assertFalse(isEnabled);
    }
}
