package com.qcadoo.localization.internal.module;

import java.io.Serializable;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.qcadoo.localization.internal.TranslationModuleService;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;

public class TranslationModuleFactory implements ModuleFactory<Module>, Serializable {

    private static final long serialVersionUID = -9206501890728921918L;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TranslationModuleService translationModuleService;

    @Override
    public void init() {
        // TODO register languages in qcadoo-plugin.xml
        // TODO register message groups in qcadoo-plugin.xml
    }

    @Override
    public Module parse(final String pluginIdentifier, final Element element) {
        String path = element.getAttributeValue("path");

        if (path == null) {
            throw new IllegalStateException("Missing path attribute of localization module");
        }

        String basename = element.getAttributeValue("basename");

        return new TranslationModule(applicationContext, translationModuleService, pluginIdentifier, basename, path);
    }

    @Override
    public String getIdentifier() {
        return "translation";
    }

}
