package com.qcadoo.security.internal.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.QcadooSecurityConstants;
import com.qcadoo.security.constants.UserFields;

@Service
public class UserRoleValidationService {

    @Autowired
    private SecurityService securityService;

    public boolean checkUserCreatingSuperadmin(final DataDefinition dataDefinition, final Entity entity) {
        final Object newValue = entity.getField(UserFields.ROLE);
        final Object oldValue = getOldValue(dataDefinition, entity);
        return checkUserCreatingSuperadmin(dataDefinition, dataDefinition.getField(UserFields.ROLE), entity, oldValue, newValue);
    }

    private Object getOldValue(final DataDefinition dataDefinition, final Entity entity) {
        if (entity.getId() == null) {
            return null;
        } else {
            final Entity existingEntity = dataDefinition.get(entity.getId());
            return existingEntity.getField(UserFields.ROLE);
        }
    }

    private boolean checkUserCreatingSuperadmin(final DataDefinition dataDefinition, final FieldDefinition fieldDefinition,
            final Entity entity, final Object oldValue, final Object newValue) {
        if (Objects.equal(oldValue, newValue) || isCurrentUserShopOrSuperAdmin(dataDefinition)
                || !QcadooSecurityConstants.ROLE_SUPERADMIN.equals(newValue)) {
            return true;
        }
        entity.addError(fieldDefinition, "qcadooUsers.validate.global.error.forbiddenRole");
        return false;
    }

    private boolean isCurrentUserShopOrSuperAdmin(final DataDefinition userDataDefinition) {
        if (isCalledFromShop()) {
            return true;
        }
        final Long currentUserId = securityService.getCurrentUserId();
        final Entity currentUserEntity = userDataDefinition.get(currentUserId);
        return QcadooSecurityConstants.ROLE_SUPERADMIN.equals(currentUserEntity.getStringField(UserFields.ROLE));
    }

    private boolean isCalledFromShop() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

}
