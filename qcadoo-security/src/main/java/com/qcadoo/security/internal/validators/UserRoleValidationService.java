package com.qcadoo.security.internal.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.QcadooSecurityConstants;

@Service
public class UserRoleValidationService {

    @Autowired
    private SecurityService securityService;

    public boolean checkUserCreatingSuperadmin(final DataDefinition dataDefinition, final Entity entity) {
        String role = entity.getStringField("role");
        if (QcadooSecurityConstants.ROLE_SUPERADMIN.equals(role) && securityService.getCurrentUserName() != null) {
            entity.addError(dataDefinition.getField("role"), "qcadooUsers.validate.global.error.forbiddenRole");
            return false;
        }
        return true;
    }
}
