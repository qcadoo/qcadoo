package com.qcadoo.view.internal.module;

import org.springframework.core.io.Resource;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

public class ViewModule extends Module {

    private final ViewDefinitionParser viewDefinitionParser;

    private final InternalViewDefinitionService viewDefinitionService;

    private final String pluginIdentifier;

    private final Resource xmlFile;

    public ViewModule(final String pluginIdentifier, final Resource xmlFile, final ViewDefinitionParser viewDefinitionParser,
            final InternalViewDefinitionService viewDefinitionService) {
        this.pluginIdentifier = pluginIdentifier;
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
        InternalViewDefinition viewDefinition = viewDefinitionParser.parseViewXml(xmlFile, pluginIdentifier);
        viewDefinitionService.save(viewDefinition);
    }

    @Override
    public void disable() {
        InternalViewDefinition viewDefinition = viewDefinitionParser.parseViewXml(xmlFile, pluginIdentifier);
        viewDefinitionService.delete(viewDefinition);
    }

}
