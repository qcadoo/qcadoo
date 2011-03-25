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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class VersionOfDependency {

    private static final Pattern PATTERN = Pattern
            .compile("((\\(|\\[)?(\\d+(.\\d+(.\\d+)?)?))??,?((\\d+(.\\d+(.\\d+)?)?)(\\)|\\])?)??");

    private final Version minVersion;

    private final boolean includeMinVersion;

    private final Version maxVersion;

    private final boolean includeMaxVersion;

    public VersionOfDependency(final String version) {
        if (StringUtils.hasText(version)) {
            Matcher matcher = PATTERN.matcher(version);

            if (matcher.matches()) {
                if (matcher.group(3) != null && matcher.group(2) == null && matcher.group(7) == null && matcher.group(10) == null) {
                    minVersion = new Version(matcher.group(3));
                    includeMinVersion = true;
                    maxVersion = minVersion;
                    includeMaxVersion = true;
                } else {
                    minVersion = matcher.group(3) != null ? new Version(matcher.group(3)) : null;
                    includeMinVersion = !"(".equals(matcher.group(2));
                    maxVersion = matcher.group(7) != null ? new Version(matcher.group(7)) : null;
                    includeMaxVersion = !"(".equals(matcher.group(10));
                }

                if (this.minVersion != null && this.maxVersion != null) {
                    int compareResult = this.minVersion.compareTo(this.maxVersion);
                    if (compareResult > 0) {
                        throw new IllegalStateException("Version " + version
                                + " is invalid: min version is larger than max version");
                    } else if (compareResult == 0 && !(includeMinVersion && includeMaxVersion)) {
                        throw new IllegalStateException("Version " + version + " is invalid: range is empty");
                    }
                }
            } else {
                throw new IllegalStateException("Version " + version + " is invalid");
            }
        } else {
            minVersion = null;
            includeMinVersion = false;
            maxVersion = null;
            includeMaxVersion = false;
        }
    }

    public boolean isVersionSatisfied(final Version version) {
        if (minVersion != null) {
            int minComparationResult = minVersion.compareTo(version);
            if (minComparationResult > 0) {
                return false;
            } else if (minComparationResult == 0 && !includeMinVersion) {
                return false;
            }
        }

        if (maxVersion != null) {
            int maxComparationResult = maxVersion.compareTo(version);
            if (maxComparationResult < 0) {
                return false;
            } else if (maxComparationResult == 0 && !includeMaxVersion) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (includeMaxVersion ? 1231 : 1237);
        result = prime * result + (includeMinVersion ? 1231 : 1237);
        result = prime * result + ((maxVersion == null) ? 0 : maxVersion.hashCode());
        result = prime * result + ((minVersion == null) ? 0 : minVersion.hashCode());
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
        if (!(obj instanceof VersionOfDependency)) {
            return false;
        }
        VersionOfDependency other = (VersionOfDependency) obj;
        if (includeMaxVersion != other.includeMaxVersion) {
            return false;
        }
        if (includeMinVersion != other.includeMinVersion) {
            return false;
        }
        if (maxVersion == null) {
            if (other.maxVersion != null) {
                return false;
            }
        } else if (!maxVersion.equals(other.maxVersion)) {
            return false;
        }
        if (minVersion == null) {
            if (other.minVersion != null) {
                return false;
            }
        } else if (!minVersion.equals(other.minVersion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String tmpMinVersion = minVersion != null ? minVersion.toString() : "0.0.0";
        if (minVersion == null && maxVersion == null) {
            return tmpMinVersion;
        } else if (minVersion != null && maxVersion == null) {
            return (includeMinVersion ? "[" : "(") + tmpMinVersion;
        } else if (minVersion == null && maxVersion != null) {
            return maxVersion.toString() + (includeMaxVersion ? "]" : ")");
        } else if (minVersion.equals(maxVersion)) {
            return tmpMinVersion;
        } else {
            return (includeMinVersion ? "[" : "(") + tmpMinVersion + "," + maxVersion.toString()
                    + (includeMaxVersion ? "]" : ")");
        }
    }
}
