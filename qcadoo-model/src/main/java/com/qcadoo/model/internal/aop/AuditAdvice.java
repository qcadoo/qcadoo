package com.qcadoo.model.internal.aop;

import java.util.Date;

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

    @Before("execution(@com.qcadoo.model.api.aop.Auditable * *(..)) &&" + "args(dataDefinition,genericEntity,..)")
    public void auditEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity) {

        if (genericEntity.getDataDefinition().isAuditable()) {
            if (genericEntity.getField("createDate") == null) {
                genericEntity.setField("createDate", new Date());
            }
            genericEntity.setField("updateDate", new Date());
        }
    }
}
