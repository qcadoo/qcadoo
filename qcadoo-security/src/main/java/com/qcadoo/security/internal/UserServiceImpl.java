package com.qcadoo.security.internal;

import static com.qcadoo.model.api.search.SearchRestrictions.eq;
import static com.qcadoo.model.api.search.SearchRestrictions.idEq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.api.UserService;
import com.qcadoo.security.constants.QcadooSecurityConstants;
import com.qcadoo.security.constants.UserFields;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private SecurityService securityService;

    @Override
    public Entity find(final Long userId) {
        return getUserDataDefinition().find().add(idEq(userId)).uniqueResult();
    }

    @Override
    public Entity find(final String userName) {
        return getUserDataDefinition().find().add(eq(UserFields.USER_NAME, userName)).uniqueResult();
    }

    @Override
    public Entity getCurrentUserEntity() {
        return find(securityService.getCurrentUserId());
    }

    @Override
    public String extractFullName(final Entity user) {
        if (user == null) {
            return null;
        }
        String firstName = user.getStringField(UserFields.FIRST_NAME);
        String lastName = user.getStringField(UserFields.LAST_NAME);
        return String.format("%s %s", firstName, lastName);
    }

    private DataDefinition getUserDataDefinition() {
        return dataDefinitionService.get(QcadooSecurityConstants.PLUGIN_IDENTIFIER, QcadooSecurityConstants.MODEL_USER);
    }
}
