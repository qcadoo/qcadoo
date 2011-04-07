package com.qcadoo.view.internal.module;

import java.util.List;

import org.springframework.core.io.Resource;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.api.ViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

public class ViewModule extends Module {

    private final ViewDefinitionParser viewDefinitionParser;

    private final InternalViewDefinitionService viewDefinitionService;

    private final Resource xmlFile;

    public ViewModule(final Resource xmlFile, final ViewDefinitionParser viewDefinitionParser,
            final InternalViewDefinitionService viewDefinitionService) {
        this.xmlFile = xmlFile;
        this.viewDefinitionParser = viewDefinitionParser;
        this.viewDefinitionService = viewDefinitionService;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        List<ViewDefinition> viewDefinitions = viewDefinitionParser.parseViewXml(xmlFile);
        for (ViewDefinition viewDefinition : viewDefinitions) {
            viewDefinitionService.save(viewDefinition);
        }
    }

    @Override
    public void disable() {
        List<ViewDefinition> viewDefinitions = viewDefinitionParser.parseViewXml(xmlFile);
        for (ViewDefinition viewDefinition : viewDefinitions) {
            viewDefinitionService.delete(viewDefinition);
        }
    }

}
