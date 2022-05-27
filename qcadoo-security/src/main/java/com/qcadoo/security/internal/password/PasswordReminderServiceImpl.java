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
package com.qcadoo.security.internal.password;

import com.google.common.base.Preconditions;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.mail.api.InvalidMailAddressException;
import com.qcadoo.mail.api.MailService;
import com.qcadoo.mail.internal.MailServiceImpl;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.PasswordReminderService;
import com.qcadoo.security.api.PasswordResetTokenService;
import com.qcadoo.security.constants.PasswordResetTokenFields;
import com.qcadoo.security.internal.api.InternalSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Objects;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@Service
public class PasswordReminderServiceImpl implements PasswordReminderService {

    @Value("${mail.company}")
    private String company;

    @Value("${mail.email}")
    private String contactMail;

    @Autowired
    private MailService mailService;

    @Autowired
    private InternalSecurityService securityService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private TranslationService translationService;

    @Override
    @Transactional
    public void generateAndSendPasswordResetLink(final String userName) throws UsernameNotFoundException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(userName), "user name should not be empty");

        Entity user = getUserEntity(userName);
        String userEmail = user.getStringField("email");

        if (!MailServiceImpl.isValidEmail(userEmail)) {
            throw new InvalidMailAddressException("invalid recipient email address");
        }

        Preconditions.checkNotNull(userEmail, "e-mail for user " + userName + " was not specified.");

        Entity passwordResetToken = passwordResetTokenService.createPasswordResetTokenForUser(user);

        sendPasswordResetLink(userEmail, userName, passwordResetToken);
    }

    private Entity getUserEntity(final String userName) {
        Entity user = securityService.getUserEntity(userName);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username " + userName + " not found");
        }

        return user;
    }

    private void updateUserPassword(final Entity user, final String password) {
        user.setField("password", password);
        user.setField("passwordConfirmation", password);

        user.getDataDefinition().save(user);
    }

    private void sendPasswordResetLink(final String userEmail, final String userName, final Entity tokenEntity) {
        String expiration = new SimpleDateFormat(DateUtils.L_DATE_TIME_FORMAT)
                .format(tokenEntity.getDateField(PasswordResetTokenFields.EXPIRATION_TIME));
        String token = tokenEntity.getStringField(PasswordResetTokenFields.TOKEN);
        String link = fromCurrentContextPath().path("passwordChange.html").queryParam("token", token).build().toString();
        String topic = translationService.translate("security.message.passwordReset.mail.topic", getLocale(), company);
        String body = translationService.translate("security.message.passwordReset.mail.body", getLocale(), userName,
                link, expiration, company, contactMail);

        mailService.sendEmail(userEmail, topic, body);
    }

}
