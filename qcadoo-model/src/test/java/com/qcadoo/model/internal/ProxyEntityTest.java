/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.6
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

import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;

public class ProxyEntityTest {

    private DataDefinition dataDefinition;

    private FieldDefinition belongsToFieldDefinition;

    private DataDefinition belongsToFieldDataDefinition;

    private static final String BELONGS_TO_FIELD_NAME = "belongsToField";

    private static final String STRING_FIELD_NAME = "stringField";

    private static final String BOOLEAN_FIELD_NAME = "booleanField";

    @Before
    public final void init() {
        belongsToFieldDefinition = mock(FieldDefinition.class);
        BelongsToType belongsToType = mock(BelongsToType.class);
        when(belongsToFieldDefinition.getType()).thenReturn(belongsToType);
        belongsToFieldDataDefinition = mock(DataDefinition.class);
        when(belongsToFieldDefinition.getDataDefinition()).thenReturn(belongsToFieldDataDefinition);

        FieldDefinition stringFieldDefinition = mock(FieldDefinition.class);
        when(stringFieldDefinition.isPersistent()).thenReturn(false);

        dataDefinition = mock(DataDefinition.class);
        FieldDefinition booleanFieldDefinition = mock(FieldDefinition.class);

        Map<String, FieldDefinition> fieldsMap = Maps.newHashMap();
        fieldsMap.put(BELONGS_TO_FIELD_NAME, belongsToFieldDefinition);
        fieldsMap.put(STRING_FIELD_NAME, stringFieldDefinition);
        fieldsMap.put(BOOLEAN_FIELD_NAME, booleanFieldDefinition);

        for (Map.Entry<String, FieldDefinition> fieldEntry : fieldsMap.entrySet()) {
            when(dataDefinition.getField(fieldEntry.getKey())).thenReturn(fieldEntry.getValue());
        }

        when(dataDefinition.getFields()).thenReturn(fieldsMap);
    }

    private ProxyEntity buildProxy(final Entity entity) {
        return new ProxyEntity(entity.getDataDefinition(), entity.getId());
    }

    @Test
    public final void shouldCopyDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        Entity copy = null;
        try {
            copy = firstProxyEntity.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstProxyEntity, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstProxyEntity, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(secondEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
    }

    @Test
    public final void shouldCopyDoNotMakeInfinityCycleWith3Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        Entity copy = null;
        try {
            copy = firstProxyEntity.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstProxyEntity, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstProxyEntity, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(secondEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
    }

    @Test
    public final void shouldCopyDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);
        fourthEntity.setId(4L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);
        final ProxyEntity fourthProxyEntity = buildProxy(fourthEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthProxyEntity);
        fourthProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        Entity copy = null;
        try {
            copy = firstProxyEntity.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstProxyEntity, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstProxyEntity, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(secondEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(fourthProxyEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME)
                .getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(fourthEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME)
                .getBelongsToField(BELONGS_TO_FIELD_NAME));
    }

    @Test
    public final void shouldHashCodesForProxyAndProxiedEntityBeEquals() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        firstEntity.setField(STRING_FIELD_NAME, "maku");
        firstEntity.setField(BOOLEAN_FIELD_NAME, true);
        secondEntity.setId(2L);
        secondEntity.setField(STRING_FIELD_NAME, "rocks");
        secondEntity.setField(BOOLEAN_FIELD_NAME, true);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        try {
            assertTrue(firstProxyEntity.hashCode() == firstEntity.hashCode());
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldHashCodeDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        int hashCode = -1;
        try {
            hashCode = firstProxyEntity.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertTrue(hashCode == firstEntity.hashCode());
    }

    @Test
    public final void shouldHashCodeDoNotMakeInfinityCycleWith3Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        int hashCode = -1;
        try {
            hashCode = firstProxyEntity.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertTrue(hashCode == firstEntity.hashCode());
    }

    @Test
    public final void shouldHashCodeDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);
        fourthEntity.setId(4L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);
        final ProxyEntity fourthProxyEntity = buildProxy(fourthEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthProxyEntity);
        fourthProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when
        int hashCode = -1;
        try {
            hashCode = firstProxyEntity.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertTrue(hashCode == firstEntity.hashCode());
    }

    @Test
    public final void shouldProxyAndProxiedEntityBeEquals() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        firstEntity.setField(STRING_FIELD_NAME, "maku");
        firstEntity.setField(BOOLEAN_FIELD_NAME, true);
        secondEntity.setId(2L);
        secondEntity.setField(STRING_FIELD_NAME, "rocks");
        secondEntity.setField(BOOLEAN_FIELD_NAME, true);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        // when & then
        try {
            Assert.assertEquals(firstEntity, firstProxyEntity);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnTrueAndDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertEquals(firstProxyEntity, firstOtherProxyEntity);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnFalseAndDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        secondOtherEntity.setField(STRING_FIELD_NAME, "difference");

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertFalse(firstProxyEntity.equals(firstOtherProxyEntity));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnTrueAndDoNotMakeInfinityCycleWith3Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);
        final ProxyEntity thirdOtherProxyEntity = buildProxy(thirdOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdOtherProxyEntity);
        thirdOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertEquals(firstProxyEntity, firstOtherProxyEntity);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnFalseAndDoNotMakeInfinityCycleWith3Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);
        thirdOtherEntity.setField(STRING_FIELD_NAME, "difference");

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);
        final ProxyEntity thirdOtherProxyEntity = buildProxy(thirdOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdOtherProxyEntity);
        thirdOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertFalse(firstProxyEntity.equals(firstOtherProxyEntity));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnTrueAndDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);
        fourthEntity.setId(4L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);
        final ProxyEntity fourthProxyEntity = buildProxy(fourthEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthProxyEntity);
        fourthProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);
        final Entity fourthOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);
        fourthOtherEntity.setId(4L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);
        when(dataDefinition.get(fourthOtherEntity.getId())).thenReturn(fourthOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);
        final ProxyEntity thirdOtherProxyEntity = buildProxy(thirdOtherEntity);
        final ProxyEntity fourthOtherProxyEntity = buildProxy(fourthOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdOtherProxyEntity);
        thirdOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthOtherProxyEntity);
        fourthOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertEquals(firstProxyEntity, firstOtherProxyEntity);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnFalseAndDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);
        fourthEntity.setId(4L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final ProxyEntity firstProxyEntity = buildProxy(firstEntity);
        final ProxyEntity secondProxyEntity = buildProxy(secondEntity);
        final ProxyEntity thirdProxyEntity = buildProxy(thirdEntity);
        final ProxyEntity fourthProxyEntity = buildProxy(fourthEntity);

        firstProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondProxyEntity);
        secondProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdProxyEntity);
        thirdProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthProxyEntity);
        fourthProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstProxyEntity);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);
        final Entity fourthOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);
        fourthOtherEntity.setId(4L);
        fourthOtherEntity.setField(STRING_FIELD_NAME, "difference");

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);
        when(dataDefinition.get(fourthOtherEntity.getId())).thenReturn(fourthOtherEntity);

        final ProxyEntity firstOtherProxyEntity = buildProxy(firstOtherEntity);
        final ProxyEntity secondOtherProxyEntity = buildProxy(secondOtherEntity);
        final ProxyEntity thirdOtherProxyEntity = buildProxy(thirdOtherEntity);
        final ProxyEntity fourthOtherProxyEntity = buildProxy(fourthOtherEntity);

        firstOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, secondOtherProxyEntity);
        secondOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, thirdOtherProxyEntity);
        thirdOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, fourthOtherProxyEntity);
        fourthOtherProxyEntity.setField(BELONGS_TO_FIELD_NAME, firstOtherProxyEntity);

        // when & then
        try {
            assertFalse(firstProxyEntity.equals(firstOtherProxyEntity));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

}
