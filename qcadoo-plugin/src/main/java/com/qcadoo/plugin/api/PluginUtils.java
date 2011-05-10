package com.qcadoo.plugin.api;

import com.qcadoo.plugin.internal.PluginUtilsService;

/**
 * Utils to checking plugin's state.
 * 
 * @since 0.4.0
 */
public class PluginUtils {

    /**
     * Returns true if plugin is enabled.
     * 
     * @param plugin
     *            plugin
     * @return true if enabled
     * @see PluginStateResolver#isEnabled(Plugin)
     */
    public static boolean isEnabled(final Plugin plugin) {
        return PluginUtilsService.isEnabled(plugin);
    }

    /**
     * Returns true if plugin is enabled.
     * 
     * @param pluginIdentifier
     *            plugin's identifier
     * @return true if enabled
     * @see PluginStateResolver#isEnabled(String)
     */
    public static boolean isEnabled(final String pluginIdentifier) {
        return PluginUtilsService.isEnabled(pluginIdentifier);
    }

}
