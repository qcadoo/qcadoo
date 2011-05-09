package com.qcadoo.security.internal.module;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.plugin.api.ModuleFactory;

public class UserModuleFactory extends ModuleFactory<UserModule> {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Value("${setAsDemoEnviroment}")
    private boolean setAsDemoEnviroment;

    @Override
    public UserModule parse(final String pluginIdentifier, final Element element) {
        String login = getRequiredAttribute(element, "login");
        String email = getAttribute(element, "email");
        String firstName = getAttribute(element, "firstName");
        String lastName = getAttribute(element, "lastName");
        String password = getRequiredAttribute(element, "password");
        String groupName = getRequiredAttribute(element, "groupName");

        checkNotNull(login, "Missing login attribute of " + getIdentifier() + " module");
        checkNotNull(password, "Missing password attribute of " + getIdentifier() + " module");
        checkNotNull(groupName, "Missing groupName attribute of " + getIdentifier() + " module");

        return new UserModule(login, email, firstName, lastName, password, groupName, setAsDemoEnviroment, dataDefinitionService);
    }

    @Override
    public String getIdentifier() {
        return "user";
    }

}
