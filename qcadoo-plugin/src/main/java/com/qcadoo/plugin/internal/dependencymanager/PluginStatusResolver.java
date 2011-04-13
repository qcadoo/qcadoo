package com.qcadoo.plugin.internal.dependencymanager;

import com.qcadoo.plugin.api.Plugin;

public interface PluginStatusResolver {

    boolean isPluginEnabled(Plugin plugin);

    boolean isPluginDisabled(Plugin plugin);

    boolean isPluginNotInstalled(Plugin plugin);

}
