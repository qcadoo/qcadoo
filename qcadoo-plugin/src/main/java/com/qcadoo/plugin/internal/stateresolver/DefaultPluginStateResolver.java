package com.qcadoo.plugin.internal.stateresolver;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginAccessor;
import com.qcadoo.plugin.api.PluginState;
import com.qcadoo.tenant.api.Standalone;

@Service
@Standalone
public class DefaultPluginStateResolver implements InternalPluginStateResolver {

    private PluginAccessor pluginAccessor;

    @Override
    public boolean isEnabled(final String pluginIdentifier) {
        Preconditions.checkState(pluginAccessor != null, "No PluginAccessor defined");
        Plugin plugin = pluginAccessor.getPlugin(pluginIdentifier);
        Preconditions.checkNotNull(plugin, "No such plugin: '" + pluginIdentifier + "'");
        return isEnabled(plugin);
    }

    @Override
    public boolean isEnabled(final Plugin plugin) {
        return PluginState.ENABLED.equals(plugin.getState());
    }

    @Override
    public void setPluginAccessor(final PluginAccessor pluginAccessor) {
        this.pluginAccessor = pluginAccessor;
    }

}
