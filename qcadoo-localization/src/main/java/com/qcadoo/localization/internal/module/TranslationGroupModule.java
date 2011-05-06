package com.qcadoo.localization.internal.module;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.Module;

public class TranslationGroupModule extends Module {

    private final InternalTranslationService translationService;

    private final String prefix;

    // TODO mady this module should contains only names and prefixes of groups
    // messages for groups should be added and removed in TranslationModule

    // private String name;

    public TranslationGroupModule(final InternalTranslationService translationService, final String prefix, final String name) {
        this.translationService = translationService;
        this.prefix = prefix;
        // this.name = name;
    }

    @Override
    public void enableOnStartup() {
        // translationService.addTranslationGroup(name, prefix);
        translationService.prepareMessagesForPrefix(prefix);
    }

}
