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

package com.qcadoo.plugin.internal.api;

public enum PluginOperationStatus {
    DEPENDENCIES_TO_ENABLE, SUCCESS, SUCCESS_WITH_RESTART, UNSATISFIED_DEPENDENCIES, DEPENDENCIES_TO_DISABLE, SYSTEM_PLUGIN_DISABLING, SUCCESS_WITH_MISSING_DEPENDENCIES, CORRUPTED_PLUGIN, CANNOT_UPLOAD_PLUGIN, CANNOT_INSTALL_PLUGIN_FILE, SYSTEM_PLUGIN_UNINSTALLING, SYSTEM_PLUGIN_UPDATING, CANNOT_DOWNGRADE_PLUGIN, DEPENDENCIES_TO_UNINSTALL, UNSATISFIED_DEPENDENCIES_AFTER_UPDATE;
}
