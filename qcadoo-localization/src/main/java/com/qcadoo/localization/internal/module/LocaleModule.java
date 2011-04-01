package com.qcadoo.localization.internal.module;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.PluginState;

public class LocaleModule extends Module {

    private InternalTranslationService translationService;

    private String locale;

    private String label;

    public LocaleModule(final InternalTranslationService translationService, final String locale, final String label) {
        this.translationService = translationService;
        this.locale = locale;
        this.label = label;
    }

    @Override
    public void init(PluginState state) {
    }

    @Override
    public void enable() {
        translationService.addLocaleToList(locale, label);
    }

    @Override
    public void disable() {
        translationService.removeLocaleToList(locale);
    }

}
