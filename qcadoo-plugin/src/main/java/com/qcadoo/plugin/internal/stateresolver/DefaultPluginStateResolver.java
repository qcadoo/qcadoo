package com.qcadoo.plugin.internal.stateresolver;

import org.springframework.stereotype.Service;

import com.qcadoo.plugin.api.PluginStateResolver;
import com.qcadoo.tenant.api.Standalone;

@Service
@Standalone
public class DefaultPluginStateResolver implements PluginStateResolver {

    @Override
    public boolean isEnabled(final String pluginIdentifier) {
        return true;
    }

}
