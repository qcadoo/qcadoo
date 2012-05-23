package com.qcadoo.plugin.internal.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.plugin.api.RunIfEnabled;

@Aspect
public class RunIfEnabledAspect {

    @Around("(execution(* *(..)) || adviceexecution()) && @annotation(annotation)")
    public Object runMethodIfEnabledAdvice(final ProceedingJoinPoint pjp, final RunIfEnabled annotation) throws Throwable {
        Object result = null;
        if (PluginUtils.isEnabled(annotation.value())) {
            result = pjp.proceed();
        }
        return result;
    }

}
