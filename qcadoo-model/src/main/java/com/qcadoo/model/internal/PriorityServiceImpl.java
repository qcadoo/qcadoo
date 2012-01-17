/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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
package com.qcadoo.model.internal;

import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.api.EntityService;
import com.qcadoo.model.internal.api.HibernateService;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.PriorityService;
import com.qcadoo.model.internal.types.PriorityType;

@Service
public final class PriorityServiceImpl implements PriorityService {

    @Autowired
    private HibernateService hibernateService;

    @Autowired
    private EntityService entityService;

    @Override
    public void prioritizeEntity(final InternalDataDefinition dataDefinition, final Object databaseEntity) {
        if (!dataDefinition.isPrioritizable()) {
            return;
        }

        FieldDefinition fieldDefinition = dataDefinition.getPriorityField();

        int totalNumberOfEntities = getTotalNumberOfEntities(dataDefinition, fieldDefinition, databaseEntity);

        entityService.setField(databaseEntity, fieldDefinition, totalNumberOfEntities + 1);
    }

    @Override
    public void deprioritizeEntity(final InternalDataDefinition dataDefinition, final Object databaseEntity) {
        if (!dataDefinition.isPrioritizable()) {
            return;
        }

        FieldDefinition fieldDefinition = dataDefinition.getPriorityField();

        int currentPriority = (Integer) entityService.getField(databaseEntity, fieldDefinition);

        changePriority(dataDefinition, fieldDefinition, databaseEntity, currentPriority + 1, Integer.MAX_VALUE, -1);
    }

    private FieldDefinition getScopeForPriority(final FieldDefinition fieldDefinition) {
        return ((PriorityType) fieldDefinition.getType()).getScopeFieldDefinition();
    }

    @Override
    public void move(final InternalDataDefinition dataDefinition, final Object databaseEntity, final int position,
            final int offset) {
        FieldDefinition fieldDefinition = dataDefinition.getPriorityField();

        int currentPriority = (Integer) entityService.getField(databaseEntity, fieldDefinition);
        int targetPriority = getTargetPriority(position, offset, currentPriority);

        targetPriority = checkIfTargetPriorityIsNotTooLow(targetPriority);
        targetPriority = getIfTargetPriorityIsNotTooHigh(dataDefinition, databaseEntity, fieldDefinition, targetPriority);

        if (currentPriority < targetPriority) {
            changePriority(dataDefinition, fieldDefinition, databaseEntity, currentPriority + 1, targetPriority, -1);
        } else if (currentPriority > targetPriority) {
            changePriority(dataDefinition, fieldDefinition, databaseEntity, targetPriority, currentPriority - 1, 1);
        } else {
            return;
        }

        entityService.setField(databaseEntity, fieldDefinition, targetPriority);
        hibernateService.getCurrentSession().update(databaseEntity);
    }

    private int getIfTargetPriorityIsNotTooHigh(final InternalDataDefinition dataDefinition, final Object databaseEntity,
            final FieldDefinition fieldDefinition, final int targetPriority) {
        if (targetPriority > 1) {
            int totalNumberOfEntities = getTotalNumberOfEntities(dataDefinition, fieldDefinition, databaseEntity);

            if (targetPriority > totalNumberOfEntities) {
                return totalNumberOfEntities;
            }
        }
        return targetPriority;
    }

    private int checkIfTargetPriorityIsNotTooLow(final int targetPriority) {
        if (targetPriority < 1) {
            return 1;
        }
        return targetPriority;
    }

    private int getTargetPriority(final int position, final int offset, final int currentPriority) {
        if (offset == 0) {
            return position;
        }
        return currentPriority + offset;
    }

    @SuppressWarnings("unchecked")
    private void changePriority(final InternalDataDefinition dataDefinition, final FieldDefinition fieldDefinition,
            final Object databaseEntity, final int fromPriority, final int toPriority, final int diff) {
        Criteria criteria = getCriteria(dataDefinition, fieldDefinition, databaseEntity).add(
                Restrictions.ge(fieldDefinition.getName(), fromPriority)).add(
                Restrictions.le(fieldDefinition.getName(), toPriority));

        List<Object> entitiesToDecrement = criteria.list();

        for (Object entity : entitiesToDecrement) {
            int priority = (Integer) entityService.getField(entity, fieldDefinition);
            entityService.setField(entity, fieldDefinition, priority + diff);
            hibernateService.getCurrentSession().update(entity);
        }
    }

    private int getTotalNumberOfEntities(final InternalDataDefinition dataDefinition, final FieldDefinition fieldDefinition,
            final Object databaseEntity) {
        Criteria criteria = getCriteria(dataDefinition, fieldDefinition, databaseEntity).setProjection(Projections.rowCount());

        return Integer.valueOf(criteria.uniqueResult().toString());
    }

    private Criteria getCriteria(final InternalDataDefinition dataDefinition, final FieldDefinition fieldDefinition,
            final Object databaseEntity) {

        Criteria criteria = hibernateService.getCurrentSession().createCriteria(dataDefinition.getClassForEntity());

        FieldDefinition scopeFieldDefinition = getScopeForPriority(fieldDefinition);

        if (scopeFieldDefinition != null) {
            Object scopeValue = entityService.getField(databaseEntity, scopeFieldDefinition);

            if (scopeValue instanceof Entity) {
                criteria.add(Restrictions.eq(scopeFieldDefinition.getName() + ".id", ((Entity) scopeValue).getId()));
            } else {
                criteria.add(Restrictions.eq(scopeFieldDefinition.getName(), scopeValue));
            }
        }

        return criteria;
    }

    @Override
    public Comparator<Entity> getEntityPriorityComparator() {
        return new Comparator<Entity>() {

            @Override
            public int compare(final Entity n1, final Entity n2) {
                Integer p1 = (Integer) n1.getField("priority");
                Integer p2 = (Integer) n2.getField("priority");
                return p1.compareTo(p2);
            }
        };
    }
}
