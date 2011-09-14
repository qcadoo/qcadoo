package com.qcadoo.model.internal.aop;

import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.InternalDataDefinition;

@Aspect
@Component
public class AuditAdvice {

    @Autowired
    DataDefinitionService dataDefinitionService;

    @AfterReturning(pointcut = "execution(@com.qcadoo.model.api.aop.Audit * *(..))", returning = "resultEntity")
    public void auditEntity(Entity resultEntity) {

        if (resultEntity.getDataDefinition().isAuditable()) {
            // entityToAudit.setField("lastUpdateDate", new Date());
        }
    }

    @Before("execution(@com.qcadoo.model.api.aop.Audit * *(..)) &&" + "args(dataDefinition,genericEntity,..)")
    public void auditEntity2(InternalDataDefinition dataDefinition, Entity genericEntity) {

        if (genericEntity.getDataDefinition().isAuditable()) {
            genericEntity.setField("lastUpdateDate", new Date());
        }
    }
}
