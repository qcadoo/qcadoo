package com.qcadoo.security.internal.permissionEvaluators;

import org.springframework.security.core.Authentication;

public interface QcadooPermisionEvaluator {

    String getTargetType();

    boolean hasPermission(Authentication authentication, String permission, String targetId);

}
