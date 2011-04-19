package com.qcadoo.plugin.api;

/**
 * Service to accessing plugins registered in the system.
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

    boolean isEnabled(final Plugin plugin);

}