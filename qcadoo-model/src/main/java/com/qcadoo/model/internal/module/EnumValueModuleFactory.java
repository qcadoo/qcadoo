package com.qcadoo.model.internal.module;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.plugin.api.ModuleFactory;

public class EnumValueModuleFactory extends ModuleFactory<EnumValueModule> {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    public EnumValueModule parse(final String pluginIdentifier, final Element element) {
        String targetPluginIdentifier = element.getAttributeValue("plugin");
        String targetModelName = element.getAttributeValue("model");
        String targetEnumFieldName = element.getAttributeValue("enum");
        String value = element.getAttributeValue("value");

        checkNotNull(targetPluginIdentifier, "Missing plugin attribute of " + getIdentifier() + " module");
        checkNotNull(targetModelName, "Missing model attribute of " + getIdentifier() + " module");
        checkNotNull(targetEnumFieldName, "Missing enum attribute of " + getIdentifier() + " module");
        checkNotNull(value, "Missing value attribute of " + getIdentifier() + " module");

        return new EnumValueModule(dataDefinitionService, targetPluginIdentifier, targetModelName, targetEnumFieldName, value);
    }

    @Override
    public String getIdentifier() {
        return "model-enum-value";
    }

}
