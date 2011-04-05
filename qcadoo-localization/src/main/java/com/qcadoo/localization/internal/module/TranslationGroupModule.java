package com.qcadoo.localization.internal.module;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.Module;

public class TranslationGroupModule extends Module {

    private final InternalTranslationService translationService;

    private final String prefix;

    // private String name;

    public TranslationGroupModule(final InternalTranslationService translationService, final String prefix, final String name) {
        this.translationService = translationService;
        this.prefix = prefix;
        // TODO mady this.name = name;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        translationService.prepareMessagesForPrefix(prefix);
    }

    @Override
    public void disable() {
    }

}
