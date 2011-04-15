package com.qcadoo.model.internal.module;

import java.util.List;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.internal.types.EnumType;
import com.qcadoo.model.internal.types.EnumTypeKey;
import com.qcadoo.plugin.api.Module;

public class EnumValueModule extends Module {

    private final String pluginIdentifier;

    private final String modelName;

    private final String fieldName;

    private final EnumTypeKey enumValue;

    private final DataDefinitionService dataDefinitionService;

    public EnumValueModule(final String originPluginIdentifier, final DataDefinitionService dataDefinitionService,
            final String pluginIdentifier, final String modelName, final String fieldName, final String value) {
        this.dataDefinitionService = dataDefinitionService;
        this.pluginIdentifier = pluginIdentifier;
        this.modelName = modelName;
        this.fieldName = fieldName;
        enumValue = new EnumTypeKey(value, originPluginIdentifier);
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        List<EnumTypeKey> keys = getKeys();

        for (EnumTypeKey key : keys) {
            if (key.equals(enumValue)) {
                return;
            }
        }

        keys.add(enumValue);
    }

    @Override
    public void disable() {
        getKeys().remove(enumValue);
    }

    private List<EnumTypeKey> getKeys() {
        return ((EnumType) dataDefinitionService.get(pluginIdentifier, modelName).getField(fieldName).getType()).getKeys();
    }

}
