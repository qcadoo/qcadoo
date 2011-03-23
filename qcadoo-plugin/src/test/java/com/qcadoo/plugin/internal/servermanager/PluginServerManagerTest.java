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

package com.qcadoo.plugin.internal.servermanager;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.qcadoo.plugin.internal.PluginException;
import com.qcadoo.plugin.internal.servermanager.DefaultPluginServerManager;

public class PluginServerManagerTest {

    private DefaultPluginServerManager defaultPluginServerManager;

    @Before
    public void init() throws IOException {
        defaultPluginServerManager = new DefaultPluginServerManager();

    }

    @Test
    public void shouldRestartServer() throws Exception {
        // given
        defaultPluginServerManager.setRestartCommand("cd");

        // when
        defaultPluginServerManager.restart();

        // then
    }

    @Test(expected = PluginException.class)
    public void shouldThrowExceptionOnRestartServerWhenIOExceptionThrown() throws Exception {
        // given
        defaultPluginServerManager.setRestartCommand("restart");

        // when
        defaultPluginServerManager.restart();

        // then

    }

}
