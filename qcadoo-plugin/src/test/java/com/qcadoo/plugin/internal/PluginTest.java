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

package com.qcadoo.plugin.internal;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginState;
import com.qcadoo.plugin.api.Version;

public class PluginTest {

    @Test
    public void shouldCallEnableOnStartupOnModules() throws Exception {
        // given
        Module module1 = mock(Module.class);
        Module module2 = mock(Module.class);

        Plugin plugin = DefaultPlugin.Builder.identifier("identifier").withModule(module1).withModule(module2).build();

        plugin.changeStateTo(PluginState.ENABLED);

        // when
        plugin.init();

        // then
        verify(module1).enableOnStartup();
        verify(module2).enableOnStartup();
        verify(module1, never()).disableOnStartup();
        verify(module2, never()).disableOnStartup();
    }

    @Test
    public void shouldCallDisableOnStartupOnModules() throws Exception {
        // given
        Module module1 = mock(Module.class);
        Module module2 = mock(Module.class);

        Plugin plugin = DefaultPlugin.Builder.identifier("identifier").withModule(module1).withModule(module2).build();

        plugin.changeStateTo(PluginState.DISABLED);

        // when
        plugin.init();

        // then
        verify(module1).disableOnStartup();
        verify(module2).disableOnStartup();
        verify(module1, never()).enableOnStartup();
        verify(module2, never()).enableOnStartup();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailInitializingPluginWithUnknownState() throws Exception {
        // given
        Plugin plugin = DefaultPlugin.Builder.identifier("identifier").build();

        // when
        plugin.init();
    }

    @Test
    public void shouldHaveUnknownStateByDefault() throws Exception {
        // given
        Plugin plugin = DefaultPlugin.Builder.identifier("identifier").build();

        // then
        assertTrue(plugin.hasState(PluginState.UNKNOWN));
        assertEquals(PluginState.UNKNOWN, plugin.getState());
    }

    @Test
    public void shouldCompareVersions() throws Exception {
        // given
        Plugin plugin1 = DefaultPlugin.Builder.identifier("identifier").withVersion("2.3.4").build();
        Plugin plugin2 = DefaultPlugin.Builder.identifier("identifier").withVersion("2.3.4").build();
        Plugin plugin3 = DefaultPlugin.Builder.identifier("identifier").withVersion("2.3.5").build();
        Plugin plugin4 = DefaultPlugin.Builder.identifier("identifier").withVersion("2.4.4").build();
        Plugin plugin5 = DefaultPlugin.Builder.identifier("identifier").withVersion("3.3.4").build();

        // then
        assertEquals(0, plugin1.compareVersion(plugin2.getVersion()));
        assertEquals(0, plugin2.compareVersion(plugin1.getVersion()));
        assertEquals(-1, plugin1.compareVersion(plugin3.getVersion()));
        assertEquals(-1, plugin1.compareVersion(plugin4.getVersion()));
        assertEquals(-1, plugin1.compareVersion(plugin5.getVersion()));
        assertEquals(-1, plugin3.compareVersion(plugin4.getVersion()));
        assertEquals(-1, plugin3.compareVersion(plugin5.getVersion()));
        assertEquals(-1, plugin4.compareVersion(plugin5.getVersion()));
        assertEquals(1, plugin5.compareVersion(plugin1.getVersion()));
        assertEquals(1, plugin5.compareVersion(plugin3.getVersion()));
        assertEquals(1, plugin5.compareVersion(plugin4.getVersion()));
        assertEquals(1, plugin4.compareVersion(plugin1.getVersion()));
        assertEquals(1, plugin4.compareVersion(plugin3.getVersion()));
        assertEquals(1, plugin3.compareVersion(plugin1.getVersion()));
    }

    @Test
    public void shouldHaveSystemFlag() throws Exception {
        // given
        Plugin plugin1 = DefaultPlugin.Builder.identifier("identifier1").asSystem().build();
        Plugin plugin2 = DefaultPlugin.Builder.identifier("identifier1").build();

        // then
        assertTrue(plugin1.isSystemPlugin());
        assertFalse(plugin2.isSystemPlugin());
    }

    @Test
    public void shouldHaveVersion() throws Exception {
        // given
        Plugin plugin = DefaultPlugin.Builder.identifier("identifier1").withVersion("1.2.3").build();

        // then
        assertEquals(new Version("1.2.3"), plugin.getVersion());
    }

    @Test
    public void shouldHaveInformation() throws Exception {
        // given
        Plugin plugin = DefaultPlugin.Builder.identifier("identifier1").withDescription("description").withName("name")
                .withVendor("vendor").withVendorUrl("vendorUrl").build();

        // then
        assertEquals("description", plugin.getPluginInformation().getDescription());
        assertEquals("name", plugin.getPluginInformation().getName());
        assertEquals("vendor", plugin.getPluginInformation().getVendor());
        assertEquals("vendorUrl", plugin.getPluginInformation().getVendorUrl());
    }

}
