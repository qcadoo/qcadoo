package com.qcadoo.plugin.api;

import java.util.Collection;

/**
 * Service to accessing plugins registered in the system.
 */
public interface PluginAccessor {

    /**
     * @param identifier
     *            plugin identifier
     * @return plugin with given identifier and status {@link PluginState#ENABLED}, otherwise null
     */
    Plugin getEnabledPlugin(String identifier);

    /**
     * @return all registered plugins with status {@link PluginState#ENABLED}
     */
    Collection<Plugin> getEnabledPlugins();

    /**
     * @param identifier
     *            plugin identifier
     * @return plugin with given identifier, otherwise null
     */
    Plugin getPlugin(String identifier);

    /**
     * @return all registered plugins
     */
    Collection<Plugin> getPlugins();

}