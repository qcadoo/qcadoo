package com.qcadoo.plugin.internal.stateresolver;

import com.qcadoo.plugin.api.PluginAccessor;
import com.qcadoo.plugin.api.PluginStateResolver;

public interface InternalPluginStateResolver extends PluginStateResolver {

    void setPluginAccessor(final PluginAccessor pluginAccessor);

}
