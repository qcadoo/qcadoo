package com.qcadoo.security.internal.password;

import static com.qcadoo.model.api.search.SearchRestrictions.eq;
import static com.qcadoo.model.api.search.SearchRestrictions.ge;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchConjunction;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.security.api.PasswordResetTokenService;
import com.qcadoo.security.constants.PasswordResetTokenFields;
import com.qcadoo.security.constants.QcadooSecurityConstants;
import com.qcadoo.security.constants.UserFields;

@Service
final class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Value("${passwordResetTokenExpirationInDays:3}")
    private Integer passwordResetTokenExpirationInDays;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Entity createPasswordResetTokenForUser(Entity userEntity) {
        DataDefinition passwordResetTokenDD = passwordResetTokenDD();
        Entity passwordResetToken = passwordResetTokenDD.create();
        passwordResetToken.setField(PasswordResetTokenFields.USER, userEntity);
        passwordResetToken.setField(PasswordResetTokenFields.EXPIRATION_TIME, generateExpirationDate());
        passwordResetToken.setField(PasswordResetTokenFields.ACTIVE, Boolean.TRUE);
        passwordResetToken.setField(PasswordResetTokenFields.TOKEN, generateToken());
        return passwordResetTokenDD.save(passwordResetToken);
    }

    @Override
    @Transactional
    public Entity processPasswordChange(String token, String password, String passwordConfirmation) {
        Entity passwordResetToken = findValidByToken(token);
        if (passwordResetToken == null) {
            throw new IllegalArgumentException();
        }
        Entity entity = updateUserPassword(passwordResetToken.getBelongsToField(PasswordResetTokenFields.USER), password,
                passwordConfirmation);
        if (entity.isValid()) {
            passwordResetToken.setActive(false);
            passwordResetTokenDD().save(passwordResetToken);
        }
        return entity;
    }

    private Entity findValidByToken(String token) {
        SearchConjunction conjunction = SearchRestrictions.conjunction();
        conjunction.add(eq(PasswordResetTokenFields.TOKEN, token));
        conjunction.add(eq(PasswordResetTokenFields.ACTIVE, Boolean.TRUE));
        conjunction.add(ge(PasswordResetTokenFields.EXPIRATION_TIME, new Date()));
        SearchResult result = passwordResetTokenDD().find().add(conjunction).list();
        return result.getTotalNumberOfEntities() == 1 ? result.getEntities().get(0) : null;
    }

    private DataDefinition passwordResetTokenDD() {
        return dataDefinitionService.get(QcadooSecurityConstants.PLUGIN_IDENTIFIER,
                QcadooSecurityConstants.MODEL_PASSWORD_RESET_TOKEN);
    }

    private Date generateExpirationDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, passwordResetTokenExpirationInDays);
        return cal.getTime();
    }

    private Entity updateUserPassword(final Entity userEntity, final String password, String passwordConfirmation) {
        userEntity.setField(UserFields.PASSWORD, password);
        userEntity.setField(UserFields.PASSWORD_CONFIRMATION, passwordConfirmation);
        userEntity.setField(UserFields.VIEW_IDENTIFIER, "userChangePassword");
        return userEntity.getDataDefinition().save(userEntity);
    }
}
