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
package com.qcadoo.security.internal.hooks;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.mail.api.MailService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.GroupFields;
import com.qcadoo.security.constants.UserFields;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Service
public class UserModelHooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModelHooks.class);

    private static final String L_SELF_DELETION_ERROR = "security.message.error.selfDeletion";

    private static final String L_QCADOOSECURITY_USER = "qcadooSecurity.user.";

    private static final String L_LABEL = ".label";

    private static final String L_QCADOOSECURITY_USER_IMPORTANT_FIELD_CHANGE_MAIL_TOPIC = "qcadooSecurity.user.importantFieldChange.mailTopic";

    private static final String L_QCADOOSECURITY_USER_IMPORTANT_FIELD_CHANGE_MAIL_BODY = "qcadooSecurity.user.importantFieldChange.mailBody";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TranslationService translationService;

    public boolean preventSelfDeletion(final DataDefinition userDD, final Entity user) {
        if (ObjectUtils.equals(securityService.getCurrentUserId(), user.getId())) {
            user.addGlobalError(L_SELF_DELETION_ERROR);

            return false;
        }

        if (user.getDateField(UserFields.LAST_ACTIVITY) != null) {
            user.addGlobalError("security.message.error.deletionOfUsedUser");
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

    public boolean validateGroupChangeDate(final DataDefinition userDD, final Entity user) {
        if (user.getId() == null) {
            user.setField(UserFields.GROUP_CHANGE_DATE, new Date());
        } else if (!userDD.get(user.getId()).getBelongsToField(UserFields.GROUP).getStringField(GroupFields.PERMISSION_TYPE).equals(user.getBelongsToField(UserFields.GROUP).getStringField(GroupFields.PERMISSION_TYPE))) {
            if (user.getDateField(UserFields.GROUP_CHANGE_DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(LocalDate.now().minusDays(7))) {
                user.addError(userDD.getField(UserFields.GROUP), "qcadooUsers.validate.global.error.forbiddenGroupChange");
                return false;
            } else {
                user.setField(UserFields.GROUP_CHANGE_DATE, new Date());
            }
        }
        return true;
    }

    public void checkIfEmailNotificationShouldBeSent(final DataDefinition userDD, final Entity user) {
        Long userId = user.getId();

        if (Objects.nonNull(userId)) {
            Entity userFromDB = userDD.get(userId);

            checkIfEmailNotificationShouldBeSent(userFromDB, user, UserFields.PASSWORD);
            checkIfEmailNotificationShouldBeSent(userFromDB, user, UserFields.EMAIL);
        }
    }

    private void checkIfEmailNotificationShouldBeSent(final Entity oldUser, final Entity newUser, final String fieldName) {
        boolean passwordMode = UserFields.PASSWORD.equals(fieldName);

        String oldString = oldUser.getStringField(fieldName);

        if (StringUtils.isEmpty(oldString)) {
            oldString = "";
        }

        String newString = newUser.getStringField(fieldName);

        if (StringUtils.isEmpty(newString)) {
            if (passwordMode) {
                //there was no field change if new string is null and this is password field
                newString = oldString;
            } else {
                newString = "";
            }
        }

        if (!oldString.equals(newString)) {
            String email = oldUser.getStringField(UserFields.EMAIL);

            if (StringUtils.isNotEmpty(email)) {
                sendEmail(email, fieldName);
            } else {
                //only log info
                LOGGER.error("Important user field change has been made, but user '{}' has not defined valid email!", new Object[]{oldUser.getStringField(UserFields.USER_NAME)});
                //do not log password value for password changes

                if (!passwordMode) {
                    LOGGER.error("The change was made for field '{}', old value is '{}' and new value is '{}'.", new Object[]{fieldName, oldString, newString});
                }
            }
        }
    }

    private void sendEmail(final String email, final String fieldName) {
        Locale locale = LocaleContextHolder.getLocale();

        String fieldTranslated = translationService.translate(L_QCADOOSECURITY_USER + fieldName + L_LABEL, locale);

        String topic = translationService.translate(L_QCADOOSECURITY_USER_IMPORTANT_FIELD_CHANGE_MAIL_TOPIC, locale, fieldTranslated);
        String body = translationService.translate(L_QCADOOSECURITY_USER_IMPORTANT_FIELD_CHANGE_MAIL_BODY, locale, fieldTranslated, getApplicationUrl());

        mailService.sendEmail(email, topic, body);
    }

    private String getApplicationUrl() {
        return new StringBuilder(httpServletRequest.getScheme()).append("://").append(httpServletRequest.getServerName()).toString();
    }

}
