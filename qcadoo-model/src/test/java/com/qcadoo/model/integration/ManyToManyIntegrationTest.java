/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.4
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.ProxyEntity;

public class ManyToManyIntegrationTest extends IntegrationTest {

    // http://docs.jboss.org/hibernate/core/3.3/reference/en/html/performance.html#performance-collections

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSaveManyToManyField() throws Exception {
        // given
        DataDefinition productDataDefinition = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PRODUCT);
        DataDefinition partDataDefinition = dataDefinitionService.get(PLUGIN_PRODUCTS_NAME, ENTITY_NAME_PART);

        Entity firstProduct = productDataDefinition.save(createProduct("asd", "00001"));
        Entity secondProduct = productDataDefinition.save(createProduct("fgh", "00002"));
        Entity thirdProduct = productDataDefinition.save(createProduct("jkl", "00003"));

        Entity firstPart = partDataDefinition.save(createPart("qwe", firstProduct,
                Lists.newArrayList(firstProduct, secondProduct)));
        Entity secondPart = partDataDefinition.save(createPart("rty", secondProduct,
                Lists.newArrayList(firstProduct, thirdProduct)));
        Entity thirdPart = partDataDefinition.save(createPart("uiop", thirdProduct,
                Lists.newArrayList(firstProduct, secondProduct, thirdProduct)));

        // when
        firstProduct = productDataDefinition.get(firstProduct.getId());
        secondProduct = productDataDefinition.get(secondProduct.getId());
        thirdProduct = productDataDefinition.get(thirdProduct.getId());

        // then
        Set<Entity> firstProductParts = (Set<Entity>) firstProduct.getField("partsManyToMany");
        assertNotNull(firstProductParts);
        assertEquals(3, firstProductParts.size());
        checkProxyCollection(partDataDefinition, firstProductParts, Lists.newArrayList(firstPart, secondPart, thirdPart));

        Set<Entity> secondProductParts = (Set<Entity>) secondProduct.getField("partsManyToMany");
        assertNotNull(secondProductParts);
        assertEquals(2, secondProductParts.size());
        checkProxyCollection(partDataDefinition, secondProductParts, Lists.newArrayList(firstPart, thirdPart));

        Set<Entity> thirdProductParts = (Set<Entity>) thirdProduct.getField("partsManyToMany");
        assertNotNull(thirdProductParts);
        assertEquals(2, thirdProductParts.size());
        checkProxyCollection(partDataDefinition, thirdProductParts, Lists.newArrayList(secondPart, thirdPart));
    }

    private void checkProxyCollection(final DataDefinition dataDefinition, final Set<Entity> proxyEntitiesSet,
            final List<Entity> entitiesList) {
        Set<Entity> loadedEntities = Sets.newHashSet();
        for (Entity proxyEntity : proxyEntitiesSet) {
            assertTrue(proxyEntity instanceof ProxyEntity);
            assertTrue(proxyEntity.isValid());
            loadedEntities.add(dataDefinition.get(proxyEntity.getId()));
        }
        assertTrue(loadedEntities.containsAll(entitiesList));
    }

}
