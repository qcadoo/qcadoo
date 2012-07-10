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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTreeNode;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;

public class EntityTreeNodeImplTest {

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

    @Test
    public final void shouldCopyDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        Entity copy = null;
        try {
            copy = firstEntityTreeNode.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstEntityTreeNode, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstEntityTreeNode, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
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

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        Entity copy = null;
        try {
            copy = firstEntityTreeNode.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstEntityTreeNode, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstEntityTreeNode, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(secondEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
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

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);
        final EntityTreeNode fourthEntityTreeNode = new EntityTreeNodeImpl(fourthEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthEntityTreeNode);
        fourthEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        Entity copy = null;
        try {
            copy = firstEntityTreeNode.copy();
        } catch (StackOverflowError e) {
            Assert.fail();
        }

        // then
        assertNotSame(firstEntityTreeNode, copy);
        assertNotSame(firstEntity, copy);
        assertEquals(firstEntityTreeNode, copy);
        assertEquals(firstEntity, copy);
        assertEquals(secondEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(secondEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(thirdEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(fourthEntityTreeNode, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME)
                .getBelongsToField(BELONGS_TO_FIELD_NAME));
        assertEquals(fourthEntity, copy.getBelongsToField(BELONGS_TO_FIELD_NAME).getBelongsToField(BELONGS_TO_FIELD_NAME)
                .getBelongsToField(BELONGS_TO_FIELD_NAME));
    }

    @Test
    public final void shouldHashCodesForTreeNodeAndWrappedEntityBeEquals() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        firstEntity.setField(STRING_FIELD_NAME, "maku");
        firstEntity.setField(BOOLEAN_FIELD_NAME, true);
        secondEntity.setId(2L);
        secondEntity.setField(STRING_FIELD_NAME, "rocks");
        secondEntity.setField(BOOLEAN_FIELD_NAME, false);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        try {
            assertEquals(firstEntityTreeNode.hashCode(), firstEntity.hashCode());
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

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        try {
            firstEntityTreeNode.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }
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

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        try {
            firstEntityTreeNode.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }
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

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);
        final EntityTreeNode fourthEntityTreeNode = new EntityTreeNodeImpl(fourthEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthEntityTreeNode);
        fourthEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        // when
        try {
            firstEntityTreeNode.hashCode();
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsTrueAndDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertEquals(firstEntityTreeNode, firstOtherEntityTreeNode);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsFalseAndDoNotMakeInfinityCycleWith2Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        secondOtherEntity.setField(STRING_FIELD_NAME, "difference");

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertFalse(firstEntityTreeNode.equals(firstOtherEntityTreeNode));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsTrueAndDoNotMakeInfinityCycleWith3Entities() {
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

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);
        final EntityTreeNode thirdOtherEntityTreeNode = new EntityTreeNodeImpl(thirdOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdOtherEntityTreeNode);
        thirdOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertEquals(firstEntityTreeNode, firstOtherEntityTreeNode);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsFalseAndDoNotMakeInfinityCycleWith3Entities() {
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

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

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

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);
        final EntityTreeNode thirdOtherEntityTreeNode = new EntityTreeNodeImpl(thirdOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdOtherEntityTreeNode);
        thirdOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertFalse(firstEntityTreeNode.equals(firstOtherEntityTreeNode));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsTrueAndDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);
        final EntityTreeNode fourthEntityTreeNode = new EntityTreeNodeImpl(fourthEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthEntityTreeNode);
        fourthEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);
        final Entity fourthOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);
        when(dataDefinition.get(fourthOtherEntity.getId())).thenReturn(fourthOtherEntity);

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);
        final EntityTreeNode thirdOtherEntityTreeNode = new EntityTreeNodeImpl(thirdOtherEntity);
        final EntityTreeNode fourthOtherEntityTreeNode = new EntityTreeNodeImpl(fourthOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdOtherEntityTreeNode);
        thirdOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthOtherEntityTreeNode);
        fourthOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertEquals(firstEntityTreeNode, firstOtherEntityTreeNode);
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldEqualsReturnsFalseAndDoNotMakeInfinityCycleWith4Entities() {
        // given
        final Entity secondEntity = new DefaultEntity(dataDefinition);
        final Entity firstEntity = new DefaultEntity(dataDefinition);
        final Entity thirdEntity = new DefaultEntity(dataDefinition);
        final Entity fourthEntity = new DefaultEntity(dataDefinition);

        firstEntity.setId(1L);
        secondEntity.setId(2L);
        thirdEntity.setId(3L);

        when(dataDefinition.get(firstEntity.getId())).thenReturn(firstEntity);
        when(dataDefinition.get(secondEntity.getId())).thenReturn(secondEntity);
        when(dataDefinition.get(thirdEntity.getId())).thenReturn(thirdEntity);
        when(dataDefinition.get(fourthEntity.getId())).thenReturn(fourthEntity);

        final EntityTreeNode firstEntityTreeNode = new EntityTreeNodeImpl(firstEntity);
        final EntityTreeNode secondEntityTreeNode = new EntityTreeNodeImpl(secondEntity);
        final EntityTreeNode thirdEntityTreeNode = new EntityTreeNodeImpl(thirdEntity);
        final EntityTreeNode fourthEntityTreeNode = new EntityTreeNodeImpl(fourthEntity);

        firstEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondEntityTreeNode);
        secondEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdEntityTreeNode);
        thirdEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthEntityTreeNode);
        fourthEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstEntityTreeNode);

        final Entity secondOtherEntity = new DefaultEntity(dataDefinition);
        final Entity firstOtherEntity = new DefaultEntity(dataDefinition);
        final Entity thirdOtherEntity = new DefaultEntity(dataDefinition);
        final Entity fourthOtherEntity = new DefaultEntity(dataDefinition);

        firstOtherEntity.setId(1L);
        secondOtherEntity.setId(2L);
        thirdOtherEntity.setId(3L);
        thirdOtherEntity.setField(STRING_FIELD_NAME, "difference");

        when(dataDefinition.get(firstOtherEntity.getId())).thenReturn(firstOtherEntity);
        when(dataDefinition.get(secondOtherEntity.getId())).thenReturn(secondOtherEntity);
        when(dataDefinition.get(thirdOtherEntity.getId())).thenReturn(thirdOtherEntity);
        when(dataDefinition.get(fourthOtherEntity.getId())).thenReturn(fourthOtherEntity);

        final EntityTreeNode firstOtherEntityTreeNode = new EntityTreeNodeImpl(firstOtherEntity);
        final EntityTreeNode secondOtherEntityTreeNode = new EntityTreeNodeImpl(secondOtherEntity);
        final EntityTreeNode thirdOtherEntityTreeNode = new EntityTreeNodeImpl(thirdOtherEntity);
        final EntityTreeNode fourthOtherEntityTreeNode = new EntityTreeNodeImpl(fourthOtherEntity);

        firstOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, secondOtherEntityTreeNode);
        secondOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, thirdOtherEntityTreeNode);
        thirdOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, fourthOtherEntityTreeNode);
        fourthOtherEntityTreeNode.setField(BELONGS_TO_FIELD_NAME, firstOtherEntityTreeNode);

        // when & then
        try {
            assertFalse(firstEntityTreeNode.equals(firstOtherEntityTreeNode));
        } catch (StackOverflowError e) {
            Assert.fail();
        }
    }

}
