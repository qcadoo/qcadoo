package com.qcadoo.localization.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.ModuleFactory;

public class LocaleModuleFactory extends ModuleFactory<LocaleModule> {

    @Autowired
    private InternalTranslationService translationService;

    @Override
    public LocaleModule parse(final String pluginIdentifier, final Element element) {
        String locale = element.getAttributeValue("locale");
        String label = element.getAttributeValue("label");

        Preconditions.checkNotNull(locale, "Missing locale attribute of locale module");
        Preconditions.checkNotNull(label, "Missing label attribute of locale module");

        return new LocaleModule(translationService, locale, label);
    }

    @Override
    public String getIdentifier() {
        return "locale";
    }

}
