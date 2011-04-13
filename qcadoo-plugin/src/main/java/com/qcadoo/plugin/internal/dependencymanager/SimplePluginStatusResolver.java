package com.qcadoo.plugin.internal.dependencymanager;

import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginState;

public class SimplePluginStatusResolver implements PluginStatusResolver {

    public boolean isPluginEnabled(final Plugin plugin) {
        return PluginState.ENABLED.equals(plugin.getState());
    }

    public boolean isPluginDisabled(final Plugin plugin) {
        return PluginState.DISABLED.equals(plugin.getState());
    }

    public boolean isPluginNotInstalled(final Plugin plugin) {
        return PluginState.TEMPORARY.equals(plugin.getState());
    }

}
