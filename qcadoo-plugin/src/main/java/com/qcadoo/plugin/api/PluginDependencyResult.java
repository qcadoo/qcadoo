package com.qcadoo.plugin.api;

import java.util.Set;

public interface PluginDependencyResult {

    /**
     * Set of plugins which have to been enabled.
     * 
     * @return plugins to enable
     */
    Set<PluginDependencyInformation> getDependenciesToEnable();

    /**
     * Set of plugins which are missing.
     * 
     * @return missing plugins
     */
    Set<PluginDependencyInformation> getUnsatisfiedDependencies();

    /**
     * Set of plugins which have to been disabled.
     * 
     * @return plugins to disable
     */
    Set<PluginDependencyInformation> getDependenciesToDisable();

    /**
     * Set of plugins which have to been uninstalled.
     * 
     * @return plugins to uninstall
     */
    Set<PluginDependencyInformation> getDependenciesToUninstall();

    /**
     * Set of plugins which have to disabled after plugin update.
     * 
     * @return plugins to disable
     */
    Set<PluginDependencyInformation> getDependenciesToDisableUnsatisfiedAfterUpdate();

    /**
     * Returns true if there is no cycle, missing and unsatisfied plugins.
     * 
     * @return true if there is no problems with dependencies between plugins
     */
    boolean isDependenciesSatisfied();

    /**
     * Returns true if there is a cycle between plugins.
     * 
     * @return true if there is a cycle
     */
    boolean isCyclic();

}
