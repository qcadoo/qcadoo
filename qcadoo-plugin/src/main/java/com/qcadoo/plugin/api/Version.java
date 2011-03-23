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

import java.io.Serializable;

public class Version implements Comparable<Version>, Serializable {

    private static final long serialVersionUID = 2842201303893250557L;

    private final int major;

    private final int minor;

    private final int branch;

    public Version(final String version) {
        String[] split = version.split("\\.");

        if (split.length > 3) {
            throw new IllegalStateException("Version " + version + " is invalid");
        }

        if (split.length > 0) {
            major = Integer.parseInt(split[0]);
        } else {
            major = 0;
        }

        if (split.length > 1) {
            minor = Integer.parseInt(split[1]);
        } else {
            minor = 0;
        }

        if (split.length > 2) {
            branch = Integer.parseInt(split[2]);
        } else {
            branch = 0;
        }
    }

    @Override
    public int compareTo(final Version otherVersion) {
        if (major < otherVersion.major) {
            return -1;
        } else if (major > otherVersion.major) {
            return 1;
        }

        if (minor < otherVersion.minor) {
            return -1;
        } else if (minor > otherVersion.minor) {
            return 1;
        }

        if (branch < otherVersion.branch) {
            return -1;
        } else if (branch > otherVersion.branch) {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + branch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + branch;
        result = prime * result + major;
        result = prime * result + minor;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        Version other = (Version) obj;
        if (branch != other.branch) {
            return false;
        }
        if (major != other.major) {
            return false;
        }
        if (minor != other.minor) {
            return false;
        }
        return true;
    }

}
