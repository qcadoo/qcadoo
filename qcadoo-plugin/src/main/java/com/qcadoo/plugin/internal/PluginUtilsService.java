package com.qcadoo.plugin.internal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginStateResolver;

/**
 * Utils to checking plugin's state.
 * 
 * @since 0.4.0
 */
@Service
public class PluginUtilsService {

    @Autowired
    private PluginStateResolver pluginStateResolver;

    private static PluginUtilsService instance;

    @PostConstruct
    public void init() {
        initialise(this);
    }

    private static void initialise(final PluginUtilsService pluginUtil) {
        PluginUtilsService.instance = pluginUtil;
    }

    /**
     * Returns true if plugin is enabled.
     * 
     * @param plugin
     *            plugin
     * @return true if enabled
     * @see PluginStateResolver#isEnabled(Plugin)
     */
    public static boolean isEnabled(final Plugin plugin) {
        return instance.pluginStateResolver.isEnabled(plugin);
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
        return instance.pluginStateResolver.isEnabled(pluginIdentifier);
    }

}
