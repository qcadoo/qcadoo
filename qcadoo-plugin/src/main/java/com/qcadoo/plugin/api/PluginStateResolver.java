package com.qcadoo.plugin.api;

/**
 * Service to checking plugin's state.
 * 
 * @since 0.4.0
 */
public interface PluginStateResolver {

    /**
     * Returns true if plugin is enabled.
     * 
     * @param pluginIdentifier
     *            plugin's identifier
     * @return true if enabled
     */
    boolean isEnabled(String pluginIdentifier);

    /**
     * Returns true if plugin is enabled.
     * 
     * @param plugin
     *            plugin
     * @return true if enabled
     */
    boolean isEnabled(final Plugin plugin);

}