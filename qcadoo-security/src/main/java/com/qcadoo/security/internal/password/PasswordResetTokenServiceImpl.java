package com.qcadoo.security.internal.password;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.qcadoo.model.api.search.SearchRestrictions.eq;
import static com.qcadoo.model.api.search.SearchRestrictions.ge;

@Service
public final class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Value("${passwordResetTokenExpirationInDays:3}")
    private Integer passwordResetTokenExpirationInDays;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Entity createPasswordResetTokenForUser(final Entity user) {
        DataDefinition passwordResetTokenDD = passwordResetTokenDD();

        Entity passwordResetToken = passwordResetTokenDD.create();

        passwordResetToken.setField(PasswordResetTokenFields.USER, user);
        passwordResetToken.setField(PasswordResetTokenFields.EXPIRATION_TIME, generateExpirationDate());
        passwordResetToken.setField(PasswordResetTokenFields.ACTIVE, Boolean.TRUE);
        passwordResetToken.setField(PasswordResetTokenFields.TOKEN, generateToken());

        return passwordResetTokenDD.save(passwordResetToken);
    }

    @Override
    @Transactional
    public Entity processPasswordChange(final String token, final String password, final String passwordConfirmation) {
        Entity passwordResetToken = findValidByToken(token);

        if (Objects.isNull(passwordResetToken)) {
            throw new IllegalArgumentException();
        }

        Entity user = updateUserPassword(passwordResetToken.getBelongsToField(PasswordResetTokenFields.USER), password,
                passwordConfirmation);

        if (user.isValid()) {
            passwordResetToken.setActive(false);

            passwordResetTokenDD().save(passwordResetToken);
        }

        return user;
    }

    private Entity findValidByToken(final String token) {
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

    private Entity updateUserPassword(final Entity user, final String password, final String passwordConfirmation) {
        user.setField(UserFields.PASSWORD, password);
        user.setField(UserFields.PASSWORD_CONFIRMATION, passwordConfirmation);
        user.setField(UserFields.VIEW_IDENTIFIER, "userChangePassword");
        user.setField(UserFields.PSWD_LAST_CHANGED, new Date());
        user.setField(UserFields.AFTER_FIRST_PSWD_CHANGE, true);

        return user.getDataDefinition().save(user);
    }

}
