package com.qcadoo.localization.internal.module;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.PluginState;

public class LocaleModule extends Module {

    private String locale;

    public LocaleModule(final String locale) {
        this.locale = locale;
    }

    @Override
    public void init(PluginState state) {
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

}
