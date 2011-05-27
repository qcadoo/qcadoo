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
package com.qcadoo.model.api.search;

import com.qcadoo.model.api.Entity;

/**
 * Object represents the criteria builder for finding entities.<br/>
 * <br/>
 * The criteria is based on the Hibernate's criteria. Please see more on the official reference: {@link http
 * ://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/querycriteria.html}.<br/>
 * <br/>
 * Examples:<br/>
 * 
 * <ul>
 * <li>dataDefinition.find().list() - select all entities from current data definition
 * <li>dataDefinition.find().setMaxResults(1).uniqueResult() - select first entity from current data definition
 * 
 * <li>where name = :name - select all entities from current data definition with given name, list of "products_product" entities
 * will be returned</li>
 * <li>from #products_product where name = :name - select all "products_product" entities with given name, list of
 * "products_product" entities will be returned</li>
 * <li>from #products_product as p where p.name = :name - select all "products_product" entities with given name, list of
 * "products_product" entities will be returned</li>
 * <li>from #products_product as p where p.vendor.name = :name - select all "products_product" entities with belongs to field
 * "vendor" with given name, list of "products_product" entities will be returned</li>
 * <li>from #products_product as p where size(p.components) > 0 - select all "products_product" entities which have components,
 * list of "products_product" entities will be returned</li>
 * <li>select p from #products_product as p where p.name = :name - select all "products_product" entities with given name, list of
 * "products_product" entities will be returned</li>
 * <li>select p, upper(p.name) from #products_product as p where p.name = :name - select all "products_product" entities with
 * given name, list of dynamic entities will be returned, field "0" will contain "products_product" entity, field "1" will contain
 * uppercased name</li>
 * <li>select p as product, upper(p.name) as name from #products_product as p where p.name = :name - select all "products_product"
 * entities with given name, list of dynamic entities will be returned, field "product" will contain "products_product" entity,
 * field "name" will contain uppercased name</li>
 * <li>from #products_product order by name asc- select all "products_product" ordered by name, list of "products_product"
 * entities will be returned</li>
 * <li>select distinct p.name as name from #products_product as p where lower(p.name) like 'a%' - select all unique names started
 * with letter "a", list of dynamic entities will be returned, field "name" will contain name</li>
 * </ul>
 * 
 * @since 0.4.1
 */
public interface SearchCriteriaBuilder {

    /**
     * Finds entities using this criteria.
     * 
     * @return search result
     */
    SearchResult list();

    /**
     * Finds unique entity.
     * 
     * @return entity
     */
    Entity uniqueResult();

    /**
     * Sets the ascending order by given field, by default there is an order by id.
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder orderAscBy(String fieldName);

    /**
     * Sets the descending order by given field, by default there is an order by id.
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder orderDescBy(String fieldName);

    /**
     * Sets the max results, by default there is no limit.
     * 
     * @param maxResults
     *            max results
     * @return this search builder
     */
    SearchCriteriaBuilder setMaxResults(int maxResults);

    /**
     * Sets the first result, by default the first result is equal to zero.
     * 
     * @param firstResult
     *            first result
     * @return this search builder
     */
    SearchCriteriaBuilder setFirstResult(int firstResult);

    /**
     * Adds projection to the criteria.
     * 
     * @param projection
     *            projection
     * @return this search builder
     * @since 0.4.1
     */
    SearchCriteriaBuilder setProjection(SearchProjection projection);

    /**
     * Adds restriction to the criteria.
     * 
     * @param criterion
     *            criterion
     * @return this search builder
     * @since 0.4.1
     */
    SearchCriteriaBuilder add(SearchCriterion criterion);

    /**
     * Adds order to the criteria.
     * 
     * @param order
     *            order
     * @return this search builder
     * @since 0.4.1
     */
    SearchCriteriaBuilder addOrder(SearchOrder order);

    /**
     * Create alias for the association to the criteria.
     * 
     * @param association
     *            association
     * @param alias
     *            alias
     * @return this search builder
     * @since 0.4.1
     */
    SearchCriteriaBuilder createAlias(String association, String alias);

    /**
     * Create create for the association to the criteria.
     * 
     * @param association
     *            association
     * @param alias
     *            alias
     * @return search builder for the subcriteria
     * @since 0.4.1
     */
    SearchCriteriaBuilder createCriteria(String association, String alias);

    /**
     * Adds the "equals to" restriction. If field has string type and value contains "%", "*", "_" or "?" the "like" restriction
     * will be used.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @see #like(String, String)
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isEq(String fieldName, Object value);

    /**
     * Adds the "like" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder like(String fieldName, String value);

    /**
     * Adds the "less than or equals to" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isLe(String fieldName, Object value);

    /**
     * Adds the "less than" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isLt(String fieldName, Object value);

    /**
     * Adds the "greater than or equals to" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isGe(String fieldName, Object value);

    /**
     * Adds the "greater than" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isGt(String fieldName, Object value);

    /**
     * Adds the "not equals to" restriction. If field has string type and value contains "%", "*", "_" or "?" the "not like"
     * restriction will be used.
     * 
     * @param fieldName
     *            field's name
     * @param value
     *            expected value
     * @see #like(String, String)
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isNe(String fieldName, Object value);

    /**
     * Adds the "not null" restriction.
     * 
     * @param fieldName
     *            field's name
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isNotNull(String fieldName);

    /**
     * Adds the "null" restriction.
     * 
     * @param fieldName
     *            field's name
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isNull(String fieldName);

    /**
     * Open "not" section. The next restriction will be negated.
     * 
     * @see #closeNot()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder openNot();

    /**
     * Close "not" section.
     * 
     * @see #openNot()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder closeNot();

    /**
     * Open "or" section. Only one restriction in this section must be met.
     * 
     * @see #closeOr()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder openOr();

    /**
     * Close "or" section.
     * 
     * @see #openOr()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder closeOr();

    /**
     * Open "and" section. All restrictions in this section must be met. This is section is opened by default.
     * 
     * @see #closeAnd()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder openAnd();

    /**
     * Close "and" section.
     * 
     * @see #openAnd()
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder closeAnd();

    /**
     * Adds the "belongs to" restriction.
     * 
     * @param fieldName
     *            field's name
     * @param entityOrId
     *            entity or its id
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder belongsTo(String fieldName, Object entityOrId);

    /**
     * Adds the "equals to" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdEq(Long id);

    /**
     * Adds the "less than or equals to" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdLe(Long id);

    /**
     * Adds the "less than" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdLt(Long id);

    /**
     * Adds the "greater than or equals to" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdGe(Long id);

    /**
     * Adds the "greater than" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdGt(Long id);

    /**
     * Adds the "not equals to" restriction for id field.
     * 
     * @param id
     *            expected id
     * 
     * @return this search builder
     * @deprecated
     */
    @Deprecated
    SearchCriteriaBuilder isIdNe(Long id);

}
