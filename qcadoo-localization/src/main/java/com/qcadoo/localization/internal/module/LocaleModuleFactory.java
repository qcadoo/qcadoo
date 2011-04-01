package com.qcadoo.localization.internal.module;

import org.jdom.Element;

import com.qcadoo.plugin.api.ModuleFactory;

public class LocaleModuleFactory implements ModuleFactory<LocaleModule> {

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public LocaleModule parse(String pluginIdentifier, Element element) {
        String locale = element.getAttributeValue("locale");

        if (locale == null) {
            throw new IllegalStateException("Missing locale attribute of localization module");
        }

        return new LocaleModule(locale);
    }

    @Override
    public String getIdentifier() {
        return "locale";
    }

}
