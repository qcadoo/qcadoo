package com.qcadoo.security.internal.module;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.Restrictions;
import com.qcadoo.plugin.api.Module;

public class UserModule extends Module {

    private final String login;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String password;

    private final String groupName;

    private final DataDefinitionService dataDefinitionService;

    public UserModule(final String login, final String email, final String firstName, final String lastName,
            final String password, final String groupName, final boolean useHardPassword,
            final DataDefinitionService dataDefinitionService) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.groupName = groupName;
        this.dataDefinitionService = dataDefinitionService;
    }

    @Override
    public void multiTenantEnableOnStartup() {
        multiTenantEnable();
    }

    @Override
    public void multiTenantEnable() {
        if (dataDefinitionService.get("qcadooSecurity", "user").find().addRestriction(Restrictions.eq("userName", login)).list()
                .getTotalNumberOfEntities() > 0) {
            return;
        }

        Entity group = dataDefinitionService.get("qcadooSecurity", "group").find()
                .addRestriction(Restrictions.eq("name", groupName)).uniqueResult();

        // TODO masz if (group == null) { }

        Entity entity = dataDefinitionService.get("qcadooSecurity", "user").create();
        entity.setField("userName", login);
        entity.setField("email", email);
        entity.setField("firstName", firstName);
        entity.setField("lastName", lastName);
        entity.setField("password", password);
        entity.setField("passwordConfirmation", password);
        entity.setField("enabled", true);
        entity.setField("userGroup", group);
        dataDefinitionService.get("qcadooSecurity", "user").save(entity);
    }

}
