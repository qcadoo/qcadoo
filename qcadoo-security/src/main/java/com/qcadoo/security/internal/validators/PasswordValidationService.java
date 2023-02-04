/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.security.internal.validators;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.constants.UserFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswordValidationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkPassword(final DataDefinition userDD, final Entity user) {
        String password = user.getStringField(UserFields.PASSWORD);
        String passwordConfirmation = user.getStringField(UserFields.PASSWORD_CONFIRMATION);
        String oldPassword = user.getStringField(UserFields.OLD_PASSWORD);

        String viewIdentifier = Objects.isNull(user.getId()) ? "userChangePassword" : user.getStringField(UserFields.VIEW_IDENTIFIER);

        if (!"profileChangePassword".equals(viewIdentifier) && !"userChangePassword".equals(viewIdentifier)) {
            return true;
        }

        if ("profileChangePassword".equals(viewIdentifier)) {
            if (Objects.isNull(oldPassword)) {
                user.addError(userDD.getField(UserFields.OLD_PASSWORD), "qcadooUsers.validate.global.error.noOldPassword");

                return false;
            }

            Object currentPassword = userDD.get(user.getId()).getField(UserFields.PASSWORD);

            if (!passwordEncoder.matches(oldPassword, currentPassword.toString())) {
                user.addError(userDD.getField(UserFields.OLD_PASSWORD), "qcadooUsers.validate.global.error.wrongOldPassword");

                return false;
            }
        }

        if (Objects.isNull(password)) {
            user.addError(userDD.getField(UserFields.PASSWORD), "qcadooUsers.validate.global.error.noPassword");

            return false;
        }

        if (Objects.isNull(passwordConfirmation)) {
            user.addError(userDD.getField(UserFields.PASSWORD_CONFIRMATION),
                    "qcadooUsers.validate.global.error.noPasswordConfirmation");

            return false;
        }

        if (!passwordEncoder.matches(passwordConfirmation, password)) {
            user.addError(userDD.getField(UserFields.PASSWORD), "qcadooUsers.validate.global.error.notMatch");
            user.addError(userDD.getField(UserFields.PASSWORD_CONFIRMATION), "qcadooUsers.validate.global.error.notMatch");

            return false;
        }

        return true;
    }

}
