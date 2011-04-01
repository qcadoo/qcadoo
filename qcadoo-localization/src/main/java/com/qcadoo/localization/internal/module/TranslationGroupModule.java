package com.qcadoo.localization.internal.module;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.PluginState;

public class TranslationGroupModule extends Module {

    private InternalTranslationService translationService;

    private String prefix;

    private String name;

    public TranslationGroupModule(final InternalTranslationService translationService, final String prefix, final String name) {
        this.translationService = translationService;
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public void init(PluginState state) {
    }

    @Override
    public void enable() {
        translationService.prepareMessagesForPrefix(prefix);
    }

    @Override
    public void disable() {
    }

}
