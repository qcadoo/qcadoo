/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
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

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.VersionableConstants;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class VersionableIntegrationTest extends IntegrationTest {

    protected static final String ENTITY_NAME_VERSIONABLE = "versionableEntity";

    private DataDefinition versionableEntityDataDefinition;

    @Before
    public final void init() {
        versionableEntityDataDefinition = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_VERSIONABLE);
    }


    @Test
    public void shouldSaveNewEntity() throws Exception {
        // given
        Entity entity = createVersionableEntity("Mr T", "t");

        // when
        entity = versionableEntityDataDefinition.save(entity);

        // then
        assertTrue(entity.isValid());

        Entity entityFromDb = fromDb(entity);

        assertEquals(Long.valueOf(0), entity.getLongField(VersionableConstants.VERSION_FIELD_NAME));
        assertEquals(Long.valueOf(0), entityFromDb.getLongField(VersionableConstants.VERSION_FIELD_NAME));
    }

    @Test
    public void shouldSaveExistingEntity() throws Exception {
        // given
        Entity entity = versionableEntityDataDefinition.get(1L);

        // when
        entity.setField("name", "Mr TT");
        entity = versionableEntityDataDefinition.save(entity);

        // then
        assertTrue(entity.isValid());

        Entity entityFromDb = fromDb(entity);

        assertEquals(Long.valueOf(1), entity.getLongField(VersionableConstants.VERSION_FIELD_NAME));
        assertEquals(Long.valueOf(1), entityFromDb.getLongField(VersionableConstants.VERSION_FIELD_NAME));
    }

    @Test
    public void shouldThrowExceptionOnConflict(){
        // given
        Entity entity1 = versionableEntityDataDefinition.get(1L);
        Entity entity2 = versionableEntityDataDefinition.get(1L);

        // when
        entity1.setField("name", "new name1");
        entity1 = versionableEntityDataDefinition.save(entity1);

        entity1.setField("name", "new name1");
        entity1 = versionableEntityDataDefinition.save(entity1);

        entity2.setField("name", "other new name1");
        entity2 = versionableEntityDataDefinition.save(entity2);

        // then
        assertTrue(entity1.isValid());
        assertFalse(entity2.isValid());
        assertEquals(1, entity2.getGlobalErrors().size());
        assertEquals("qcadooView.validate.global.optimisticLock", entity2.getGlobalErrors().get(0).getMessage());
    }

    protected Entity createVersionableEntity(final String name, final String number) {
        Entity entity = versionableEntityDataDefinition.create();
        entity.setField("name", name);
        entity.setField("number", number);

        return entity;
    }
}
