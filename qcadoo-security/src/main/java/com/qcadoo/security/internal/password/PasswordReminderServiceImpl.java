package com.qcadoo.security.internal.password;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.mail.api.InvalidMailAddressException;
import com.qcadoo.mail.api.MailService;
import com.qcadoo.mail.internal.MailServiceImpl;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.PasswordGeneratorService;
import com.qcadoo.security.api.PasswordReminderService;
import com.qcadoo.security.internal.api.InternalSecurityService;

@Service
public class PasswordReminderServiceImpl implements PasswordReminderService {

    @Autowired
    private MailService mailService;

    @Autowired
    private InternalSecurityService securityService;

    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    @Autowired
    private TranslationService translationService;

    @Override
    @Transactional
    public void generateAndSendNewPassword(final String userName) throws UsernameNotFoundException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(userName), "user name should not be empty");
        Entity userEntity = getUserEntity(userName);
        String userEmail = userEntity.getStringField("email");
        if (!MailServiceImpl.isValidEmail(userEmail)) {
            throw new InvalidMailAddressException("invalid recipient email address");
        }
        Preconditions.checkNotNull(userEmail, "e-mail for user " + userName + " was not specified.");
        String newPassword = passwordGeneratorService.generatePassword();

        updateUserPassword(userEntity, newPassword);
        sendNewPassword(userEmail, userName, newPassword);
    }

    private Entity getUserEntity(final String userName) {
        Entity userEntity = securityService.getUserEntity(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username " + userName + " not found");
        }
        return userEntity;
    }

    private void updateUserPassword(final Entity userEntity, final String password) {
        userEntity.setField("password", password);
        userEntity.setField("passwordConfirmation", password);
        userEntity.getDataDefinition().save(userEntity);
    }

    private void sendNewPassword(final String userEmail, final String userName, final String newPassword) {
        String topic = translationService.translate("security.message.passwordReset.mail.topic", getLocale());
        String body = translationService
                .translate("security.message.passwordReset.mail.body", getLocale(), userName, newPassword);
        mailService.sendEmail(userEmail, topic, body);
    }

}
