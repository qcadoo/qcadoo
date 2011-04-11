package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

public class ViewTabModuleFactory extends ModuleFactory<ViewTabModule> {

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Autowired
    private ViewDefinitionParser viewDefinitionParser;

    @Override
    public ViewTabModule parse(final String pluginIdentifier, final Element element) {
        String resource = element.getAttributeValue("resource");

        if (resource == null) {
            throw new IllegalStateException("Missing resource attribute of view module");
        }

        return new ViewTabModule(new ClassPathResource(pluginIdentifier + "/" + resource), viewDefinitionService,
                viewDefinitionParser);
    }

    @Override
    public String getIdentifier() {
        return "view-tab";
    }

}
