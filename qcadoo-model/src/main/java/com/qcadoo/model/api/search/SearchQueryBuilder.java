package com.qcadoo.model.api.search;

import java.math.BigDecimal;
import java.util.Date;

import com.qcadoo.model.api.Entity;

/**
 * Object represents the query builder for finding entities.
 * 
 * @since 0.4.1
 */
public interface SearchQueryBuilder {

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
     * Sets the max results, by default there is no limit.
     * 
     * @param maxResults
     *            max results
     * @return this query builder
     */
    SearchQueryBuilder setMaxResults(int maxResults);

    /**
     * Sets the first result, by default the first result is equal to zero.
     * 
     * @param firstResult
     *            first result
     * @return this query builder
     */
    SearchQueryBuilder setFirstResult(int firstResult);

    /**
     * Sets the "string" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setString(String name, String val);

    /**
     * Sets the "boolean" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setBoolean(String name, boolean val);

    /**
     * Sets the "byte" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setByte(String name, byte val);

    /**
     * Sets the "short" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setShort(String name, short val);

    /**
     * Sets the "integer" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setInteger(String name, int val);

    /**
     * Sets the "long" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setLong(String name, long val);

    /**
     * Sets the "float" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setFloat(String name, float val);

    /**
     * Sets the "double" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setDouble(String name, double val);

    /**
     * Sets the "bigDecimal" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setBigDecimal(String name, BigDecimal val);

    /**
     * Sets the "date" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setDate(String name, Date val);

    /**
     * Sets the "time" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setTime(String name, Date val);

    /**
     * Sets the "timestamp" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setTimestamp(String name, Date date);

    /**
     * Sets the "entity" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param entity
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setEntity(String name, Entity entity);

    /**
     * Sets the "entity" parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param id
     *            entity's id
     * @param modelName
     *            entity's model
     * @param pluginIdentifier
     *            entity's plugin
     * @return this query builder
     */
    SearchQueryBuilder setEntity(String name, String pluginIdentifier, String modelName, long id);

    /**
     * Sets the parameter for given placeholder.
     * 
     * @param name
     *            placeholder
     * @param val
     *            value of the parameter
     * @return this query builder
     */
    SearchQueryBuilder setParameter(String name, Object val);

}
