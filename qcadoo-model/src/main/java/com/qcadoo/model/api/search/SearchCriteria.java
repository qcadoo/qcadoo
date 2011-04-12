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

package com.qcadoo.model.api.search;

import java.util.Set;

import com.qcadoo.model.api.DataDefinition;

/**
 * Object represents the criteria for finding entities. It is used for building SQL query.
 * 
 * @since 0.4.0
 */
public interface SearchCriteria {

    /**
     * Returns max results.
     * 
     * @return max results
     * @see SearchCriteriaBuilder#setMaxResults(int)
     */
    int getMaxResults();

    /**
     * Returns first result.
     * 
     * @return first result
     * @see SearchCriteriaBuilder#setFirstResult(int)
     */
    int getFirstResult();

    /**
     * Returns search order.
     * 
     * @return order
     */
    Order getOrder();

    /**
     * Returns list of search restrictions.
     * 
     * @return restrictions
     * @see SearchCriteriaBuilder#addRestriction(Restriction)
     */
    Set<Restriction> getRestrictions();

    /**
     * Returns data definition for searching entities.
     * 
     * @return data definition
     */
    DataDefinition getDataDefinition();

}
