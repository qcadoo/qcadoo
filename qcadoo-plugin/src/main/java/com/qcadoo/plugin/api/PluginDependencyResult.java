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

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Holder for plugin's dependencies information.
 * 
 * @see PluginManager
 * @see PluginOperationResult
 * @since 0.4.0
 */
public final class PluginDependencyResult {

    private Set<PluginDependencyInformation> dependenciesToEnable = Sets.newHashSet();

    private Set<PluginDependencyInformation> unsatisfiedDependencies = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToDisable = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToDisableUnsatisfiedAfterUpdate = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToUninstall = Sets.newHashSet();

    private boolean cycleExists;

    private PluginDependencyResult() {
    }

    /**
     * Set of plugins which have to been enabled.
     * 
     * @return plugins to enable
     */
    public Set<PluginDependencyInformation> getDependenciesToEnable() {
        return dependenciesToEnable;
    }

    /**
     * Set of plugins which are missing.
     * 
     * @return missing plugins
     */
    public Set<PluginDependencyInformation> getUnsatisfiedDependencies() {
        return unsatisfiedDependencies;
    }

    /**
     * Set of plugins which have to been disabled.
     * 
     * @return plugins to disable
     */
    public Set<PluginDependencyInformation> getDependenciesToDisable() {
        return dependenciesToDisable;
    }

    /**
     * Set of plugins which have to been uninstalled.
     * 
     * @return plugins to uninstall
     */
    public Set<PluginDependencyInformation> getDependenciesToUninstall() {
        return dependenciesToUninstall;
    }

    /**
     * Set of plugins which have to disabled after plugin update.
     * 
     * @return plugins to disable
     */
    public Set<PluginDependencyInformation> getDependenciesToDisableUnsatisfiedAfterUpdate() {
        return dependenciesToDisableUnsatisfiedAfterUpdate;
    }

    /**
     * Returns true if there is no cycle, missing and unsatisfied plugins.
     * 
     * @return true if there is no problems with dependencies between plugins
     */
    public boolean isDependenciesSatisfied() {
        return dependenciesToEnable.isEmpty() && unsatisfiedDependencies.isEmpty() && dependenciesToDisable.isEmpty()
                && dependenciesToUninstall.isEmpty() && dependenciesToDisableUnsatisfiedAfterUpdate.isEmpty() && !isCyclic();
    }

    /**
     * Returns true if there is a cycle between plugins.
     * 
     * @return true if there is a cycle
     */
    public boolean isCyclic() {
        return cycleExists;
    }

    /**
     * Creates holder with plugins to enable.
     * 
     * @param dependenciesToEnable
     *            plugins to enable
     * @return holder
     */
    public static PluginDependencyResult dependenciesToEnable(final Set<PluginDependencyInformation> dependenciesToEnable) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToEnable(dependenciesToEnable);
        return result;
    }

    /**
     * Creates holder with all plugins satisfied.
     * 
     * @return holder
     */
    public static PluginDependencyResult satisfiedDependencies() {
        return new PluginDependencyResult();
    }

    /**
     * Creates holder with missing plugins.
     * 
     * @param unsatisfiedDependencies
     *            missing plugins
     * @return holder
     */
    public static PluginDependencyResult unsatisfiedDependencies(final Set<PluginDependencyInformation> unsatisfiedDependencies) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setUnsatisfiedDependencies(unsatisfiedDependencies);
        return result;
    }

    /**
     * Creates holder with plugins to disable.
     * 
     * @param dependenciesToDisable
     *            plugins to disable
     * @return holder
     */
    public static PluginDependencyResult dependenciesToDisable(final Set<PluginDependencyInformation> dependenciesToDisable) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToDisable(dependenciesToDisable);
        return result;
    }

    /**
     * Creates holder with plugins to uninstall.
     * 
     * @param dependenciesToUninstall
     *            plugins to uninstall
     * @return holder
     */
    public static PluginDependencyResult dependenciesToUninstall(final Set<PluginDependencyInformation> dependenciesToUninstall) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToUninstall(dependenciesToUninstall);
        return result;
    }

    /**
     * Creates holder with plugins to disable, after update.
     * 
     * TODO mina what is the different between dependenciesToDisableUnsatisfiedAfterUpdate and dependenciesToDisable
     * 
     * @param dependenciesToDisable
     *            plugins to disable
     * @param dependenciesToDisableUnsatisfiedAfterUpdate
     *            plugins to disable
     * @return holder
     */
    public static PluginDependencyResult dependenciesToUpdate(final Set<PluginDependencyInformation> dependenciesToDisable,
            final Set<PluginDependencyInformation> dependenciesToDisableUnsatisfiedAfterUpdate) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToDisable(dependenciesToDisable);
        result.setDependenciesToDisableUnsatisfiedAfterUpdate(dependenciesToDisableUnsatisfiedAfterUpdate);
        return result;
    }

    /**
     * Creates holder with plugins to disable, after update.
     * 
     * @return holder
     */
    public static PluginDependencyResult cyclicDependencies() {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setCycleExists(true);
        return result;
    }

    private void setDependenciesToUninstall(final Set<PluginDependencyInformation> dependenciesToUninstall) {
        this.dependenciesToUninstall = dependenciesToUninstall;
    }

    private void setCycleExists(final boolean cycleExists) {
        this.cycleExists = cycleExists;
    }

    private void setDependenciesToEnable(final Set<PluginDependencyInformation> dependenciesToEnable) {
        this.dependenciesToEnable = dependenciesToEnable;
    }

    private void setUnsatisfiedDependencies(final Set<PluginDependencyInformation> unsatisfiedDependencies) {
        this.unsatisfiedDependencies = unsatisfiedDependencies;
    }

    private void setDependenciesToDisable(final Set<PluginDependencyInformation> dependenciesToDisable) {
        this.dependenciesToDisable = dependenciesToDisable;
    }

    private void setDependenciesToDisableUnsatisfiedAfterUpdate(
            final Set<PluginDependencyInformation> dependenciesToDisableUnsatisfiedAfterUpdate) {
        this.dependenciesToDisableUnsatisfiedAfterUpdate = dependenciesToDisableUnsatisfiedAfterUpdate;
    }
}
