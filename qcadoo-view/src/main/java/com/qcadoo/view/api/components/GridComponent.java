/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
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
package com.qcadoo.view.api.components;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.view.api.ComponentState;

/**
 * Represents grid component
 * 
 * @since 0.4.0
 */
public interface GridComponent extends ComponentState {

    /**
     * Gets ids of all selected entities
     * 
     * @return ids of all selected entities
     */
    Set<Long> getSelectedEntitiesIds();

    /**
     * Sets new collection of selected entities
     * 
     * @param selectedEntities
     *            ids of selected entities
     */
    void setSelectedEntitiesIds(final Set<Long> selectedEntities);

    /**
     * Sets new content of grid
     * 
     * <b>Warning:</b> Paging, searching and sorting can not work when grid content is put directly by this method.
     * 'setCustomRestriction' should be used instead wherever possible
     * 
     * @param entities
     *            new content of grid
     */
    void setEntities(List<Entity> entities);

    /**
     * Returns current content of grid
     * 
     * @return all content entities of this grid
     */
    List<Entity> getEntities();

    Map<String, String> getColumnNames(Locale locale);

    List<Map<String, String>> getColumnValues(Locale locale);

    /**
     * Adds restriction to this grid
     * 
     * @param customRestriction
     *            additional restriction to this grid
     */
    void setCustomRestriction(CustomRestriction customRestriction);

    /**
     * Defines if this grid should be editable
     * 
     * @param isEditable
     *            true if this grid should be editable, false if not
     */
    void setEditable(boolean isEditable);

}