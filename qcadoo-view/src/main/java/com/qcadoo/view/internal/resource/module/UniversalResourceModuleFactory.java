package com.qcadoo.view.internal.resource.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.resource.ResourceService;

public class UniversalResourceModuleFactory extends ModuleFactory<UniversalResourceModule> {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public UniversalResourceModule parse(final String pluginIdentifier, final Element element) {
        String uri = getRequiredAttribute(element, "uri");
        return new UniversalResourceModule(resourceService, applicationContext, pluginIdentifier, uri);
    }

    @Override
    public String getIdentifier() {
        return "resource";
    }
}
