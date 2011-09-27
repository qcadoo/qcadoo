/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.8
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
package com.qcadoo.view.internal.ribbon.model;

import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.Ribbon;

public interface InternalRibbon extends Ribbon {

    /**
     * Set identifier of this ribbon
     * 
     * @param name
     *            identifier of this ribbon
     */
    void setName(String name);

    /**
     * Add group to this ribbon
     * 
     * @param group
     *            group to add
     */
    void addGroupsPack(RibbonGroupsPack groupPack);

    /**
     * Removes group from this ribbon
     * 
     * @param group
     *            group to remove
     */
    void removeGroupsPack(RibbonGroupsPack groupPack);

    /**
     * generates JSON string that contains all ribbon definition
     * 
     * @return JSON ribbon definition
     */
    JSONObject getAsJson();

    /**
     * Gets copy of this robbon - internal usage only
     * 
     * @return copy of this ribbon
     */
    InternalRibbon getCopy();

    /**
     * Gets ribbon with only updated fields - internal usage only
     * 
     * @return ribon with only updated fields
     */
    InternalRibbon getUpdate();
}
