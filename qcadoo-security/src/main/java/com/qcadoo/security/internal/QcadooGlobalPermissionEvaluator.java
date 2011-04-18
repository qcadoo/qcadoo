package com.qcadoo.security.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.qcadoo.security.internal.permissionEvaluators.QcadooPermisionEvaluator;

public class QcadooGlobalPermissionEvaluator implements PermissionEvaluator {

    private final Map<String, QcadooPermisionEvaluator> evaluators = new HashMap<String, QcadooPermisionEvaluator>();

    public void setQcadooEvaluators(final Set<QcadooPermisionEvaluator> evaluatorsSet) {
        for (QcadooPermisionEvaluator evaluator : evaluatorsSet) {
            evaluators.put(evaluator.getTargetType(), evaluator);
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        QcadooPermisionEvaluator evaluator = evaluators.get(targetType);
        if (evaluator == null) {
            throw new IllegalArgumentException("there is no evaluator for target type '" + targetType + "'");
        }

        return evaluator.hasPermission(authentication, (String) permission, (String) targetId);
    }

}
