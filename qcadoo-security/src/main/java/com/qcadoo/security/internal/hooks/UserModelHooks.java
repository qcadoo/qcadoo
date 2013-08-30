package com.qcadoo.security.internal.hooks;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;

@Service
public class UserModelHooks {

    private static final String SELF_DELETION_ERROR = "security.message.error.selfDeletion";

    @Autowired
    private SecurityService securityService;

    public boolean preventSelfDeletion(final DataDefinition userDD, final Entity user) {
        if (ObjectUtils.equals(securityService.getCurrentUserId(), user.getId())) {
            user.addGlobalError(SELF_DELETION_ERROR);
            return false;
        }
        return true;
    }

}
