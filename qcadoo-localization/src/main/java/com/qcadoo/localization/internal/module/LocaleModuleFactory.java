package com.qcadoo.localization.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.ModuleFactory;

public class LocaleModuleFactory extends ModuleFactory<LocaleModule> {

    @Autowired
    private InternalTranslationService translationService;

    @Override
    public LocaleModule parse(final String pluginIdentifier, final Element element) {
        String locale = getRequiredAttribute(element, "locale");
        String label = getRequiredAttribute(element, "label");

        return new LocaleModule(translationService, locale, label);
    }

    @Override
    public String getIdentifier() {
        return "translation-locale";
    }

}
