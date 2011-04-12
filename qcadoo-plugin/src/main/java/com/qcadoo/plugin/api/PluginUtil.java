package com.qcadoo.plugin.api;

public class PluginUtil {

    public static boolean isPluginEnabled(final Plugin plugin) {
        return plugin.hasState(PluginState.ENABLED) || plugin.hasState(PluginState.ENABLING);
    }

}
