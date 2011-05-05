package com.qcadoo.security.internal.validators;

import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;

@Service
public class PasswordValidationService {

    public boolean checkPassword(final DataDefinition dataDefinition, final Entity entity) {
        String password = entity.getStringField("password");
        String passwordConfirmation = entity.getStringField("passwordConfirmation");
        String oldPassword = entity.getStringField("oldPassword");
        String viewIdentifier = entity.getId() == null ? "userChangePassword" : entity.getStringField("viewIdentifier");

        if (!"profileChangePassword".equals(viewIdentifier) && !"userChangePassword".equals(viewIdentifier)) {
            return true;
        }

        if ("profileChangePassword".equals(viewIdentifier)) {
            if (oldPassword == null) {
                entity.addError(dataDefinition.getField("oldPassword"), "qcadooUsers.validate.global.error.noOldPassword");
                return false;
            }
            Object currentPassword = dataDefinition.get(entity.getId()).getField("password");

            if (!currentPassword.equals(oldPassword)) {
                entity.addError(dataDefinition.getField("oldPassword"), "qcadooUsers.validate.global.error.wrongOldPassword");
                return false;
            }
        }

        if (password == null) {
            entity.addError(dataDefinition.getField("password"), "qcadooUsers.validate.global.error.noPassword");
            return false;
        }

        if (passwordConfirmation == null) {
            entity.addError(dataDefinition.getField("passwordConfirmation"),
                    "qcadooUsers.validate.global.error.noPasswordConfirmation");
            return false;
        }

        if (!password.equals(passwordConfirmation)) {
            entity.addError(dataDefinition.getField("password"), "qcadooUsers.validate.global.error.notMatch");
            entity.addError(dataDefinition.getField("passwordConfirmation"), "qcadooUsers.validate.global.error.notMatch");
            return false;
        }

        return true;
    }
}
