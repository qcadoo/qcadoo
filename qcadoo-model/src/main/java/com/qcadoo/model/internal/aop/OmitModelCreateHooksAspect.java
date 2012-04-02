package com.qcadoo.model.internal.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class OmitModelCreateHooksAspect {

    @Pointcut("execution(* com.qcadoo.model.internal.DataAccessServiceImpl+.copy(com.qcadoo.model.internal.api.InternalDataDefinition, Long...))")
    public void copyEntity() {
    }

    @Pointcut("execution(boolean com.qcadoo.model.internal.api.InternalDataDefinition+.callCreateHook(com.qcadoo.model.api.Entity))")
    public void callCreateHook() {
    }

    @Around("callCreateHook() && cflowbelow(copyEntity())")
    public boolean omitCreateHookDuringCopy(final ProceedingJoinPoint pjp) throws Throwable {
        return true;
    }

}
