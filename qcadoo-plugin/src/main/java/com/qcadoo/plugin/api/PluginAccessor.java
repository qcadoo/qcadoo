package com.qcadoo.plugin.api;

import java.util.Collection;

/**
 * Service to accessing plugins registered in the system.
 * 
 * @since 0.4.0
 */
public interface PluginAccessor {

    /**
     * Returns plugin with given identifier and status {@link PluginState#ENABLED}.
     * 
     * @param identifier
     *            plugin's identifier
     * @return enabled plugin or null if not found
     */
    Plugin getEnabledPlugin(String identifier);

    /**
     * Returns all registered plugins with status {@link PluginState#ENABLED}.
     * 
     * @return enabled plugins
     */
    Collection<Plugin> getEnabledPlugins();

    /**
     * Returns plugin with given identifier.
     * 
     * @param identifier
     *            plugin's identifier
     * @return plugin or null if not found
     */
    Plugin getPlugin(String identifier);

    /**
     * Returns all registered plugins.
     * 
     * @return plugins
     */
    Collection<Plugin> getPlugins();

    /**
     * Returns true if plugin is enabled.
     * 
     * @param pluginIdentifier
     *            plugin's identifier
     * @return true if enabled
     */
    boolean isEnabled(String pluginIdentifier);

}