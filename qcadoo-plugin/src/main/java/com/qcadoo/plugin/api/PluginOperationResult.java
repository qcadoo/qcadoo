/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */

package com.qcadoo.plugin.api;


public final class PluginOperationResult {

    private final PluginOperationStatus status;

    private final PluginDependencyResult pluginDependencyResult;

    private PluginOperationResult(final PluginOperationStatus status) {
        this.status = status;
        this.pluginDependencyResult = PluginDependencyResult.satisfiedDependencies();
    }

    private PluginOperationResult(final PluginOperationStatus status, final PluginDependencyResult pluginDependencyResult) {
        this.status = status;
        this.pluginDependencyResult = pluginDependencyResult;
    }

    public static PluginOperationResult successWithRestart() {
        return new PluginOperationResult(PluginOperationStatus.SUCCESS_WITH_RESTART);
    }

    public static PluginOperationResult success() {
        return new PluginOperationResult(PluginOperationStatus.SUCCESS);
    }

    public static PluginOperationResult systemPluginDisabling() {
        return new PluginOperationResult(PluginOperationStatus.SYSTEM_PLUGIN_DISABLING);
    }

    public static PluginOperationResult systemPluginUninstalling() {
        return new PluginOperationResult(PluginOperationStatus.SYSTEM_PLUGIN_UNINSTALLING);
    }

    public static PluginOperationResult systemPluginUpdating() {
        return new PluginOperationResult(PluginOperationStatus.SYSTEM_PLUGIN_UPDATING);
    }

    public static PluginOperationResult corruptedPlugin() {
        return new PluginOperationResult(PluginOperationStatus.CORRUPTED_PLUGIN);
    }

    public static PluginOperationResult cannotUploadPlugin() {
        return new PluginOperationResult(PluginOperationStatus.CANNOT_UPLOAD_PLUGIN);
    }

    public static PluginOperationResult cannotDowngradePlugin() {
        return new PluginOperationResult(PluginOperationStatus.CANNOT_DOWNGRADE_PLUGIN);
    }

    public static PluginOperationResult dependenciesToEnable(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.DEPENDENCIES_TO_ENABLE, pluginDependencyResult);
    }

    public static PluginOperationResult unsatisfiedDependencies(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.UNSATISFIED_DEPENDENCIES, pluginDependencyResult);
    }

    public static PluginOperationResult successWithMissingDependencies(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.SUCCESS_WITH_MISSING_DEPENDENCIES, pluginDependencyResult);
    }

    public PluginOperationStatus getStatus() {
        return status;
    }

    public PluginDependencyResult getPluginDependencyResult() {
        return pluginDependencyResult;
    }

    public static PluginOperationResult dependenciesToDisable(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.DEPENDENCIES_TO_DISABLE, pluginDependencyResult);
    }

    public static PluginOperationResult dependenciesToUninstall(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.DEPENDENCIES_TO_UNINSTALL, pluginDependencyResult);
    }

    public static PluginOperationResult unsatisfiedDependenciesAfterUpdate(final PluginDependencyResult pluginDependencyResult) {
        return new PluginOperationResult(PluginOperationStatus.UNSATISFIED_DEPENDENCIES_AFTER_UPDATE, pluginDependencyResult);
    }

    public boolean isSuccess() {
        switch (status) {
            case SUCCESS:
            case SUCCESS_WITH_RESTART:
            case SUCCESS_WITH_MISSING_DEPENDENCIES:
                return true;
            default:
                return false;
        }
    }

    public boolean isRestartNeccessary() {
        return PluginOperationStatus.SUCCESS_WITH_RESTART.equals(status);
    }

    public static PluginOperationResult cannotInstallPlugin() {
        return new PluginOperationResult(PluginOperationStatus.CANNOT_INSTALL_PLUGIN_FILE);
    }
}
