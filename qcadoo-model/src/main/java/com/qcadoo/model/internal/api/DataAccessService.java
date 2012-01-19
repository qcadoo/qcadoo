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
package com.qcadoo.model.internal.api;

import java.util.List;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.internal.search.SearchCriteria;
import com.qcadoo.model.internal.search.SearchQuery;

public interface DataAccessService {

    InternalDataDefinition getDataDefinition(String pluginIdentifier, String name);

    Entity save(InternalDataDefinition dataDefinition, Entity entity);

    Entity get(InternalDataDefinition dataDefinition, Long entityId);

    List<Entity> copy(InternalDataDefinition dataDefinition, Long... entityId);

    void delete(InternalDataDefinition dataDefinition, Long... entityId);

    SearchResult find(SearchCriteria searchCriteria);

    SearchResult find(SearchQuery searchQuery);

    void moveTo(InternalDataDefinition dataDefinition, Long entityId, int position);

    void move(InternalDataDefinition dataDefinition, Long entityId, int offset);

    Object convertToDatabaseEntity(Entity entity);

    List<Entity> deactivate(InternalDataDefinition dataDefinition, Long... entityId);

    List<Entity> activate(InternalDataDefinition dataDefinition, Long... entityId);
}
