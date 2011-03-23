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

package com.qcadoo.plugin.internal.module;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;

public class ModuleFactoryAccessorTest {

    @Test
    public void shouldCallPostInitializeOnAllModuleFactories() throws Exception {
        // given
        ModuleFactory<?> moduleFactory1 = mock(ModuleFactory.class);
        given(moduleFactory1.getIdentifier()).willReturn("module1");
        ModuleFactory<?> moduleFactory2 = mock(ModuleFactory.class);
        given(moduleFactory2.getIdentifier()).willReturn("module2");

        DefaultModuleFactoryAccessor moduleFactoryAccessor = new DefaultModuleFactoryAccessor();
        List<ModuleFactory<?>> factoriesList = new ArrayList<ModuleFactory<?>>();
        factoriesList.add(moduleFactory1);
        factoriesList.add(moduleFactory2);
        moduleFactoryAccessor.setModuleFactories(factoriesList);

        // when
        moduleFactoryAccessor.init();

        // then
        InOrder inOrder = inOrder(moduleFactory1, moduleFactory2);
        inOrder.verify(moduleFactory1).init();
        inOrder.verify(moduleFactory2).init();
    }

    @Test
    public void shouldReturnModuleFactory() throws Exception {
        // given
        ModuleFactory<?> moduleFactory = mock(ModuleFactory.class);
        given(moduleFactory.getIdentifier()).willReturn("module");

        DefaultModuleFactoryAccessor moduleFactoryAccessor = new DefaultModuleFactoryAccessor();
        moduleFactoryAccessor.setModuleFactories(Collections.<ModuleFactory<?>> singletonList(moduleFactory));

        // when
        ModuleFactory<? extends Module> mf = moduleFactoryAccessor.getModuleFactory("module");

        // then
        Assert.assertSame(moduleFactory, mf);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfModuleFactoryNotExists() throws Exception {
        // given
        DefaultModuleFactoryAccessor moduleFactoryAccessor = new DefaultModuleFactoryAccessor();
        moduleFactoryAccessor.setModuleFactories(Collections.<ModuleFactory<?>> emptyList());

        // when
        moduleFactoryAccessor.getModuleFactory("module");
    }
}
