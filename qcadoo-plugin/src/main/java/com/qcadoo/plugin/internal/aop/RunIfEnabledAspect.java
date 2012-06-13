package com.qcadoo.plugin.internal.aop;

import static com.qcadoo.plugin.api.PluginUtils.isEnabled;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.qcadoo.plugin.api.RunIfEnabled;

@Aspect
public class RunIfEnabledAspect {

    @Around("(execution(* *(..)) || adviceexecution()) && @annotation(annotation)")
    public Object runMethodIfEnabledAdvice(final ProceedingJoinPoint pjp, final RunIfEnabled annotation) throws Throwable {
        return runIfEnabled(pjp, annotation);
    }

    @Around("(execution(* *(..)) || adviceexecution()) && @within(annotation) && !@annotation(com.qcadoo.plugin.api.RunIfEnabled)")
    public Object runClassMethodIfEnabledAdvice(final ProceedingJoinPoint pjp, final RunIfEnabled annotation) throws Throwable {
        return runIfEnabled(pjp, annotation);
    }

    private Object runIfEnabled(final ProceedingJoinPoint pjp, final RunIfEnabled annotation) throws Throwable {
        Object result = null;
        if (pluginsAreEnabled(annotation.value())) {
            result = pjp.proceed();
        }
        return result;
    }

    private boolean pluginsAreEnabled(final String[] pluginIdentifiers) {
        for (String pluginIdentifier : pluginIdentifiers) {
            if (!isEnabled(pluginIdentifier)) {
                return false;
            }
        }
        return true;
    }
}
