package com.qcadoo.model.internal.module;

import java.util.List;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.internal.types.EnumType;
import com.qcadoo.plugin.api.Module;

public class EnumValueModule extends Module {

    private final String pluginIdentifier;

    private final String modelName;

    private final String fieldName;

    private final String value;

    private final DataDefinitionService dataDefinitionService;

    public EnumValueModule(final DataDefinitionService dataDefinitionService, final String pluginIdentifier,
            final String modelName, final String fieldName, final String value) {
        this.dataDefinitionService = dataDefinitionService;
        this.pluginIdentifier = pluginIdentifier;
        this.modelName = modelName;
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        List<String> keys = getKeys();

        for (String key : keys) {
            if (key.equals(value)) {
                return;
            }
        }

        keys.add(value);
    }

    @Override
    public void disable() {
        getKeys().remove(value);
    }

    private List<String> getKeys() {
        return ((EnumType) dataDefinitionService.get(pluginIdentifier, modelName).getField(fieldName).getType()).getKeys();
    }

}
