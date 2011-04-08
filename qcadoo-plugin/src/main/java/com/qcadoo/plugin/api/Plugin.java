package com.qcadoo.plugin.api;

import java.util.Set;

import org.jdom.Element;

/**
 * Plugin represents information from plugin descriptor and holds all its modules.
 * 
 * Plugin, {@link Module} and {@link ModuleFactory} are strongly connected. Below shows licecycle methods in the particular
 * situations.
 * 
 * <h3>Application startup</h3>
 * 
 * For every module factory, in proper order:
 * 
 * <ul>
 * <li>{@link ModuleFactory#parse(String, Element)}</li>
 * <li>{@link ModuleFactory#preInit()}</li>
 * <li>{@link Module#init()} for every module belongs to this module factory, in plugin dependency order</li>
 * <li>{@link ModuleFactory#postInit()}</li>
 * </ul>
 * 
 * Again for every module factory, in proper order:
 * 
 * <ul>
 * <li>{@link Module#enableOnStartup()} or {@link Module#disableOnStartup()} for every module belongs to this module factory, in
 * plugin dependency order</li>
 * <li>{@link Module#multiTenantEnableOnStartup()} or {@link Module#multiTenantDisableOnStartup()} for every module belongs to
 * this module factory, for every tenant, in plugin dependency order</li>
 * </ul>
 * 
 * For every module factory with state {@link PluginState#ENABLING}:
 * 
 * <ul>
 * <li>{@link Module#enable()}</li>
 * <li>{@link Module#multiTenantEnable()} for every tenant</li>
 * </ul>
 * 
 * <h3>Plugin enabling</h3>
 * 
 * For plugin in {@link PluginState#DISABLED} state.
 * 
 * <ul>
 * <li>{@link Module#enable()}</li>
 * <li>{@link Module#multiTenantEnable()} for every tenant</li>
 * </ul>
 * 
 * For plugin in {@link PluginState#TEMPORARY} state, the state is changed to {@link PluginState#ENABLING} and the system is
 * restarted.
 * 
 * <h3>Plugin disabling</h3>
 * 
 * <ul>
 * <li>{@link Module#disable()}</li>
 * <li>{@link Module#multiTenantDisable()} for every tenant</li>
 * </ul>
 * 
 * <h3>Plugin installing</h3>
 * 
 * No additional method is called.
 * 
 * <h3>Plugin uninstalling</h3>
 * 
 * If the plugin is enabled, it will be disabled first. The system will be restarted.
 * 
 * <h3>Plugin updating</h3>
 * 
 * If the plugin is enabled, it will be disabled first. The state is set to {@link PluginState#ENABLING} and the system is
 * restarted.
 * 
 */
public interface Plugin {

    /**
     * @return plugin identifier, it is unique in the whole system
     */
    String getIdentifier();

    /**
     * @return version of the plugin
     */
    Version getVersion();

    /**
     * @return state of the plugin, only {@link PluginState#ENABLED} plugins are usable in system
     */
    PluginState getState();

    /**
     * @return additional information
     */
    PluginInformation getPluginInformation();

    /**
     * @return requirements information
     */
    Set<PluginDependencyInformation> getRequiredPlugins();

    /**
     * @return true for system plugin, it cannot be disabled and removed
     */
    boolean isSystemPlugin();

    /**
     * @return name the file with plugin
     */
    String getFilename();

    /**
     * @see Version#compareTo(Version)
     */
    int compareVersion(Version version);

    /**
     * @param expectedState
     *            expected state
     * @return true if plugin is in expected state
     */
    boolean hasState(PluginState expectedState);

}