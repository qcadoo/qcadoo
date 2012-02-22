/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.3
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
package com.qcadoo.model.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.ValidationService;

public class ValidationServiceImplTest {

    private ValidationService validationService;

    private Entity genericEntity;

    private Entity existingGenericEntity;

    private InternalDataDefinition dataDefinition;

    @Before
    public final void init() {
        genericEntity = mock(Entity.class);
        existingGenericEntity = mock(Entity.class);
        dataDefinition = mock(InternalDataDefinition.class);

        validationService = new ValidationServiceImpl();
    }

    @Test
    public final void shouldCallCreateHooksIfEntityIsValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(true);
        when(genericEntity.getId()).thenReturn(null);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, existingGenericEntity);

        // then
        verify(dataDefinition).callCreateHook(genericEntity);

    }

    @Test
    public final void shouldCallUpdateHooksIfEntityIsValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(true);
        when(genericEntity.getId()).thenReturn(1L);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, existingGenericEntity);

        // then
        verify(dataDefinition).callUpdateHook(genericEntity);

    }

    @Test
    public final void shouldNotCallModelHooksIfEntityIsNotValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(false);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, null);

        // then
        verify(dataDefinition, never()).callCreateHook(genericEntity);
        verify(dataDefinition, never()).callUpdateHook(genericEntity);
    }

}
