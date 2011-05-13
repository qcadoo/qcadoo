package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

public class ViewRibbonModuleFactory extends ModuleFactory<ViewRibbonModule> {

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Autowired
    private ViewDefinitionParser viewDefinitionParser;

    @Override
    protected ViewRibbonModule parseElement(final String pluginIdentifier, final Element element) {
        String resource = getRequiredAttribute(element, "resource");

        return new ViewRibbonModule(pluginIdentifier, new ClassPathResource(pluginIdentifier + "/" + resource),
                viewDefinitionService, viewDefinitionParser);
    }

    @Override
    public String getIdentifier() {
        return "view-ribbon-group";
    }

}
