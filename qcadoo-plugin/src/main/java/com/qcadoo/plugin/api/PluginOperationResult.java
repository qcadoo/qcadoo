package com.qcadoo.plugin.api;


/**
 * Holder for the status and dependencies information returned by {@link PluginManager} methods.
 * 
 * @since 0.4.0
 */
public interface PluginOperationResult {

    /**
     * Returns true if operation was successful: {@link PluginOperationStatus#SUCCESS},
     * {@link PluginOperationStatus#SUCCESS_WITH_MISSING_DEPENDENCIES} and {@link PluginOperationStatus#SUCCESS_WITH_RESTART}.
     * 
     * @return true if success
     */
    boolean isSuccess();

    /**
     * Returns true if operation requires restart: {@link PluginOperationStatus#SUCCESS_WITH_RESTART}.
     * 
     * @return true if success
     */
    boolean isRestartNeccessary();

    /**
     * Returns status.
     * 
     * @return status
     */
    PluginOperationStatus getStatus();

    /**
     * Returns dependencies information.
     * 
     * @return dependencies information
     */
    PluginDependencyResult getPluginDependencyResult();

}
