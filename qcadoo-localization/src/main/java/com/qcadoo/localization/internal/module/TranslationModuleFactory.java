package com.qcadoo.localization.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.localization.internal.InternalTranslationService;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;

public class TranslationModuleFactory implements ModuleFactory<Module> {

    @Autowired
    private InternalTranslationService translationService;

    @Override
    public void init() {
        // TODO
        // register languages
        // move it to qcadoo-plugin.xml
        translationService.prepareMessagesForPrefix("commons");
        translationService.prepareMessagesForPrefix("secority");
        translationService.prepareMessagesForPrefix("core.dashboard");
    }

    @Override
    public Module parse(final String pluginIdentifier, final Element element) {
        throw new IllegalStateException("Translation module cannot be instantiated");
    }

    @Override
    public String getIdentifier() {
        return "#translation";
    }

}
