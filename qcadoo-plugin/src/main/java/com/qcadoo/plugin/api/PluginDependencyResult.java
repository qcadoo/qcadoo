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

public final class PluginDependencyResult {

    private Set<PluginDependencyInformation> dependenciesToEnable = Sets.newHashSet();

    private Set<PluginDependencyInformation> unsatisfiedDependencies = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToDisable = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToDisableUnsatisfiedAfterUpdate = Sets.newHashSet();

    private Set<PluginDependencyInformation> dependenciesToUninstall = Sets.newHashSet();

    private boolean cycleExists;

    private PluginDependencyResult() {
    }

    public Set<PluginDependencyInformation> getDependenciesToEnable() {
        return dependenciesToEnable;
    }

    public Set<PluginDependencyInformation> getUnsatisfiedDependencies() {
        return unsatisfiedDependencies;
    }

    public Set<PluginDependencyInformation> getDependenciesToDisable() {
        return dependenciesToDisable;
    }

    public Set<PluginDependencyInformation> getDependenciesToUninstall() {
        return dependenciesToUninstall;
    }

    public Set<PluginDependencyInformation> getDependenciesToDisableUnsatisfiedAfterUpdate() {
        return dependenciesToDisableUnsatisfiedAfterUpdate;
    }

    public static PluginDependencyResult dependenciesToEnable(final Set<PluginDependencyInformation> dependenciesToEnable) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToEnable(dependenciesToEnable);
        return result;
    }

    public static PluginDependencyResult satisfiedDependencies() {
        return new PluginDependencyResult();
    }

    public static PluginDependencyResult unsatisfiedDependencies(final Set<PluginDependencyInformation> unsatisfiedDependencies) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setUnsatisfiedDependencies(unsatisfiedDependencies);
        return result;
    }

    public static PluginDependencyResult dependenciesToDisable(final Set<PluginDependencyInformation> dependenciesToDisable) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToDisable(dependenciesToDisable);
        return result;
    }

    public static PluginDependencyResult dependenciesToUninstall(final Set<PluginDependencyInformation> dependenciesToUninstall) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToUninstall(dependenciesToUninstall);
        return result;
    }

    public static PluginDependencyResult dependenciesToUpdate(final Set<PluginDependencyInformation> dependenciesToDisable,
            final Set<PluginDependencyInformation> dependenciesToDisableUnsatisfiedAfterUpdate) {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setDependenciesToDisable(dependenciesToDisable);
        result.setDependenciesToDisableUnsatisfiedAfterUpdate(dependenciesToDisableUnsatisfiedAfterUpdate);
        return result;
    }

    public static PluginDependencyResult cyclicDependencies() {
        PluginDependencyResult result = new PluginDependencyResult();
        result.setCycleExists(true);
        return result;
    }

    public boolean isDependenciesSatisfied() {
        return dependenciesToEnable.isEmpty() && unsatisfiedDependencies.isEmpty() && dependenciesToDisable.isEmpty()
                && dependenciesToUninstall.isEmpty() && dependenciesToDisableUnsatisfiedAfterUpdate.isEmpty() && !isCyclic();
    }

    public boolean isCyclic() {
        return cycleExists;
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
