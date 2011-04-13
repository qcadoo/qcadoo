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

import com.qcadoo.model.api.Entity;

/**
 * Object represents the criteria builer for finding entities.
 * 
 * @see com.qcadoo.model.api.search.SearchCriteria
 * @since 0.4.0
 */
public interface SearchCriteriaBuilder {

    /**
     * Finds entities using this criteria.
     * 
     * @see com.qcadoo.model.internal.api.DataAccessService#find(SearchCriteria)
     * @return search result
     */
    SearchResult list();

    /**
     * Finds unique entity.
     * 
     * @see com.qcadoo.model.internal.api.DataAccessService#find(SearchCriteria)
     * @return entity
     */
    Entity uniqueResult();

    /**
     * Adds the restriction.
     * 
     * @param restriction
     *            restriction
     * @see SearchCriteria#getRestrictions()
     * @return this search builder
     */
    SearchCriteriaBuilder addRestriction(Restriction restriction);

    /**
     * Sets the ascending order by given field, by default there is an order by id.
     * 
     * @see SearchCriteria#getOrder()
     * @see Order#asc(String)
     * @return this search builder
     */
    SearchCriteriaBuilder setOrderAscBy(String fieldName);

    /**
     * Sets the descending order by given field, by default there is an order by id.
     * 
     * @see SearchCriteria#getOrder()
     * @see Order#desc(String)
     * @return this search builder
     */
    SearchCriteriaBuilder setOrderDescBy(String fieldName);

    /**
     * Sets the max results, by default there is no limit.
     * 
     * @param maxResults
     *            max results
     * @see SearchCriteria#getMaxResults()
     * @return this search builder
     */
    SearchCriteriaBuilder setMaxResults(int maxResults);

    /**
     * Sets the first result, by default the first result is equal to zero.
     * 
     * @param firstResult
     *            first result
     * @see SearchCriteria#getFirstResult()
     * @return this search builder
     */
    SearchCriteriaBuilder setFirstResult(int firstResult);

}
