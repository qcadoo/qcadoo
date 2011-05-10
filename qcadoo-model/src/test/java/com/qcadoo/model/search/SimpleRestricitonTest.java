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

package com.qcadoo.model.search;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.IlikeExpression;
import org.hibernate.criterion.NotNullExpression;
import org.hibernate.criterion.NullExpression;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.impl.CriteriaImpl;
import org.junit.Before;
import org.junit.Test;

import com.qcadoo.model.beans.sample.SampleSimpleDatabaseObject;
import com.qcadoo.model.internal.DataAccessTest;
import com.qcadoo.model.internal.search.Restriction;
import com.qcadoo.model.internal.search.RestrictionOperator;
import com.qcadoo.model.internal.search.restrictions.IsNotNullRestriction;
import com.qcadoo.model.internal.search.restrictions.IsNullRestriction;
import com.qcadoo.model.internal.search.restrictions.LikeRestriction;
import com.qcadoo.model.internal.search.restrictions.SimpleRestriction;

public final class SimpleRestricitonTest extends DataAccessTest {

    private SampleSimpleDatabaseObject simpleDatabaseObject = null;

    private Criteria criteria = null;

    @Before
    public void init() {
        simpleDatabaseObject = new SampleSimpleDatabaseObject(1L);
        simpleDatabaseObject.setName("Mr T");
        simpleDatabaseObject.setAge(66);

        criteria = new CriteriaImpl(null, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithEqRestriction() {
        // given
        Restriction restriction = new SimpleRestriction(fieldDefinitionName.getName(), simpleDatabaseObject.getName(),
                RestrictionOperator.EQ);

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            SimpleExpression simpleExpression = (SimpleExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name=" + simpleDatabaseObject.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithGeRestriction() {
        // given
        Restriction restriction = new SimpleRestriction(fieldDefinitionName.getName(), simpleDatabaseObject.getName(),
                RestrictionOperator.GE);

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            SimpleExpression simpleExpression = (SimpleExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name>=" + simpleDatabaseObject.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithGtRestriction() {
        // given
        Restriction restriction = new SimpleRestriction(fieldDefinitionName.getName(), simpleDatabaseObject.getName(),
                RestrictionOperator.GT);

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            SimpleExpression simpleExpression = (SimpleExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name>" + simpleDatabaseObject.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithLeRestriction() {
        // given
        Restriction restriction = new SimpleRestriction(fieldDefinitionName.getName(), simpleDatabaseObject.getName(),
                RestrictionOperator.LE);

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            SimpleExpression simpleExpression = (SimpleExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name<=" + simpleDatabaseObject.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithLtRestriction() {
        // given
        Restriction restriction = new SimpleRestriction(fieldDefinitionName.getName(), simpleDatabaseObject.getName(),
                RestrictionOperator.LT);

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            SimpleExpression simpleExpression = (SimpleExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name<" + simpleDatabaseObject.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithIsNullRestriction() {
        // given
        Restriction restriction = new IsNullRestriction(fieldDefinitionName.getName());

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            NullExpression nullExpression = (NullExpression) entry.getCriterion();
            assertEquals(nullExpression.toString(), "name is null");
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithIsNotNullRestriction() {
        // given
        Restriction restriction = new IsNotNullRestriction(fieldDefinitionName.getName());

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            NotNullExpression notNullExpression = (NotNullExpression) entry.getCriterion();
            assertEquals(notNullExpression.toString(), "name is not null");
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreateCriteriaWithStringLikeRestriction() {
        // given
        Restriction restriction = new LikeRestriction(fieldDefinitionName.getName(), "%Mr__%");

        // when
        criteria.add(restriction.addToHibernateCriteria(criteria));

        // then
        for (Iterator<CriteriaImpl.CriterionEntry> criterionIterator = ((CriteriaImpl) criteria).iterateExpressionEntries(); criterionIterator
                .hasNext();) {
            CriteriaImpl.CriterionEntry entry = criterionIterator.next();
            IlikeExpression simpleExpression = (IlikeExpression) entry.getCriterion();
            assertEquals(simpleExpression.toString(), "name ilike " + "%Mr__" + "%");
        }
    }

}
