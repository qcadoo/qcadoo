package com.qcadoo.plugin.internal.dependencymanager;

import java.io.Serializable;
import java.util.Comparator;

import com.qcadoo.plugin.api.Plugin;

public class DependencyComparator implements Comparator<Plugin>, Serializable {

    private static final long serialVersionUID = 1821666538483568800L;

    @Override
    public int compare(final Plugin o1, final Plugin o2) {
        if (o1.isSystemPlugin() && !o2.isSystemPlugin()) {
            return -1;
        } else if (!o1.isSystemPlugin() && o2.isSystemPlugin()) {
            return 1;
        }
        return 0;
    }
}
