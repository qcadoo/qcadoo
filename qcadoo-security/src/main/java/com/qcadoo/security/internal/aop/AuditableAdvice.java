/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.10
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
package com.qcadoo.security.internal.aop;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.security.api.SecurityService;

@Aspect
@Component
public class AuditableAdvice {

    @Autowired
    DataDefinitionService dataDefinitionService;

    @Autowired
    SecurityService securityService;

    @Before("execution(@com.qcadoo.model.api.aop.Auditable * *(..)) &&" + "args(dataDefinition,genericEntity,..)")
    public void auditEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity) {

        if (genericEntity.getDataDefinition().isAuditable()) {
            if (genericEntity.getId() == null) {
                genericEntity.setField("createDate", new Date());
                genericEntity.setField("createUser", securityService.getCurrentUserName());
            }
            genericEntity.setField("updateDate", new Date());
            genericEntity.setField("updateUser", securityService.getCurrentUserName());
        }
    }
}
