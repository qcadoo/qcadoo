/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.2
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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.api.types.FieldType;

public class DefaultEntityTest {

    private DefaultEntity defaultEntity;

    private DataDefinition dataDefinition;

    private FieldDefinition belongsToFieldDefinition;

    private DataDefinition belongsToFieldDataDefinition;

    private static final String BELONGS_TO_FIELD_NAME = "belongsToField";

    private static final String STRING_FIELD_NAME = "stringField";

    private static final String BOOLEAN_FIELD_NAME = "booleanField";

    @Before
    public final void init() {
        belongsToFieldDefinition = mock(FieldDefinition.class);
        FieldType belongsToType = mock(BelongsToType.class);
        when(belongsToFieldDefinition.getType()).thenReturn(belongsToType);
        belongsToFieldDataDefinition = mock(DataDefinition.class);
        when(belongsToFieldDefinition.getDataDefinition()).thenReturn(belongsToFieldDataDefinition);

        dataDefinition = mock(DataDefinition.class);
        when(dataDefinition.getField(BELONGS_TO_FIELD_NAME)).thenReturn(belongsToFieldDefinition);
        FieldDefinition stringFieldDefinition = mock(FieldDefinition.class);
        when(dataDefinition.getField(STRING_FIELD_NAME)).thenReturn(stringFieldDefinition);
        FieldDefinition booleanFieldDefinition = mock(FieldDefinition.class);
        when(dataDefinition.getField(BOOLEAN_FIELD_NAME)).thenReturn(booleanFieldDefinition);

        defaultEntity = new DefaultEntity(dataDefinition);
    }

    @Test
    public final void shouldGetBelongsToFieldReturnEntity() throws Exception {
        // given
        Entity belongsToEntity = mock(Entity.class);
        defaultEntity.setField(BELONGS_TO_FIELD_NAME, belongsToEntity);

        // when
        Entity returnedEntity = defaultEntity.getBelongsToField(BELONGS_TO_FIELD_NAME);

        // then
        assertEquals(belongsToEntity, returnedEntity);
    }

    @Test
    public final void shouldGetBelongsToFieldReturnProxyEntity() throws Exception {
        // given
        Long belongsToEntityId = 1L;
        defaultEntity.setField(BELONGS_TO_FIELD_NAME, belongsToEntityId);

        // when
        Entity returnedEntity = defaultEntity.getBelongsToField(BELONGS_TO_FIELD_NAME);

        // then
        assertEquals(belongsToEntityId, returnedEntity.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldGetBelongsToFieldThrowIllegalArgumentExceptionIfFieldIsNotABelongsToType() throws Exception {
        // given
        FieldType wrongFieldType = mock(FieldType.class);
        when(belongsToFieldDefinition.getType()).thenReturn(wrongFieldType);
        defaultEntity.setField(BELONGS_TO_FIELD_NAME, "some wrong type value");

        // when
        defaultEntity.getBelongsToField(BELONGS_TO_FIELD_NAME);
    }

    @Test
    public final void shouldGetBelongsToFieldReturnNullIfFieldIsEmpty() throws Exception {
        // when
        Entity returnedEntity = defaultEntity.getBelongsToField(BELONGS_TO_FIELD_NAME);

        // then
        assertNull(returnedEntity);
    }

    @Test
    public final void shouldGetDataDefinitionReturnDataDefinition() throws Exception {
        // when
        DataDefinition returnedDataDefinition = defaultEntity.getDataDefinition();

        // then
        assertEquals(dataDefinition, returnedDataDefinition);
    }

    @Test
    public final void shouldCopyReturnCopyOfEntity() throws Exception {
        // given
        Entity belongsToEntity = mock(Entity.class);
        when(belongsToEntity.getDataDefinition()).thenReturn(belongsToFieldDataDefinition);
        when(belongsToEntity.copy()).thenReturn(belongsToEntity);

        defaultEntity.setId(1L);
        defaultEntity.setField(BELONGS_TO_FIELD_NAME, belongsToEntity);
        defaultEntity.setField(STRING_FIELD_NAME, "some string value");

        // when
        Entity returnedCopy = defaultEntity.copy();

        // then
        assertEquals(defaultEntity, returnedCopy);
        assertEquals(defaultEntity.hashCode(), returnedCopy.hashCode());
        assertNotSame(defaultEntity, returnedCopy);
    }

    @Test
    public final void shouldGetBooleanValueReturnTrueIfFieldValueIsTrue() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, true);

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertTrue(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnFalseIfFieldValueIsFalse() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, false);

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertFalse(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnTrueIfFieldValueIsStringTrue() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, "true");

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertTrue(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnTrueIfFieldValueIsStringUpperCaseTrue() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, "TRUE");

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertTrue(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnFalseIfFieldValueIsStringFalse() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, "false");

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertFalse(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnFalseIfFieldValueIsStringOtherThanTrueOrFalse() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, "some other string");

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertFalse(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnFalseIfFieldValueIsNotAppropriateType() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, 1);

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertFalse(returnedValue);
    }

    @Test
    public final void shouldGetBooleanValueReturnFalseIfFieldValueIsNull() throws Exception {
        // given
        defaultEntity.setField(BOOLEAN_FIELD_NAME, null);

        // when
        boolean returnedValue = defaultEntity.getBooleanField(BOOLEAN_FIELD_NAME);

        // then
        assertFalse(returnedValue);
    }
}
