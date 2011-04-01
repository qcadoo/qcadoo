package com.qcadoo.localization.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.ModuleFactory;

public class LocaleModuleFactory implements ModuleFactory<LocaleModule> {

    @Autowired
    private InternalTranslationService translationService;

    @Override
    public void init() {
    }

    @Override
    public LocaleModule parse(String pluginIdentifier, Element element) {
        String locale = element.getAttributeValue("locale");

        Preconditions.checkNotNull(locale, "Missing locale attribute of localization module");

        return new LocaleModule(translationService, locale);
    }

    @Override
    public String getIdentifier() {
        return "locale";
    }

}
