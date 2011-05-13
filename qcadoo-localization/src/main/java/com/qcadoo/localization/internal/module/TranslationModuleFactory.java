package com.qcadoo.localization.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.qcadoo.localization.internal.TranslationModuleService;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;

public class TranslationModuleFactory extends ModuleFactory<Module> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TranslationModuleService translationModuleService;

    @Override
    protected Module parseElement(final String pluginIdentifier, final Element element) {
        String path = getRequiredAttribute(element, "path");
        String basename = getAttribute(element, "basename");

        return new TranslationModule(applicationContext, translationModuleService, pluginIdentifier, basename, path);
    }

    @Override
    public String getIdentifier() {
        return "translation";
    }

}
