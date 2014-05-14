package com.qcadoo.security.internal.hooks;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.UserFields;

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

    public void setDefaultNames(final DataDefinition userDD, final Entity user) {
        replaceByUserNameIfBlank(user, UserFields.FIRST_NAME);
        replaceByUserNameIfBlank(user, UserFields.LAST_NAME);
    }

    private void replaceByUserNameIfBlank(final Entity user, final String fieldName) {
        String fieldValue = user.getStringField(fieldName);
        if (StringUtils.isBlank(fieldValue)) {
            user.setField(fieldName, user.getStringField(UserFields.USER_NAME));
        }
    }

}
