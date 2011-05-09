package com.qcadoo.model.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.plugin.api.ModuleFactory;

public class EnumValueModuleFactory extends ModuleFactory<EnumValueModule> {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    public EnumValueModule parse(final String pluginIdentifier, final Element element) {
        String targetPluginIdentifier = getRequiredAttribute(element, "plugin");
        String targetModelName = getRequiredAttribute(element, "model");
        String targetEnumFieldName = getRequiredAttribute(element, "enum");
        String value = getRequiredAttribute(element, "value");

        return new EnumValueModule(pluginIdentifier, dataDefinitionService, targetPluginIdentifier, targetModelName,
                targetEnumFieldName, value);
    }

    @Override
    public String getIdentifier() {
        return "model-enum-value";
    }

}
