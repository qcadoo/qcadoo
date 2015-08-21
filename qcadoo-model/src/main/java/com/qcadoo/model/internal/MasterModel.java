package com.qcadoo.model.internal;

public class MasterModel {
    private String pluginIdentifier;

    private String name;

    public MasterModel() {
    }

    public MasterModel(String pluginIdentifier, String name) {
        this.pluginIdentifier = pluginIdentifier;
        this.name = name;
    }

    public String getPluginIdentifier() {
        return pluginIdentifier;
    }

    public void setPluginIdentifier(String pluginIdentifier) {
        this.pluginIdentifier = pluginIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
