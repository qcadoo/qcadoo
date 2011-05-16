package com.qcadoo.view.internal.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.components.window.WindowComponentPattern;
import com.qcadoo.view.internal.ribbon.InternalRibbonGroup;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserException;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;
import com.qcadoo.view.internal.xml.ViewExtension;

public class ViewRibbonModule extends Module {

    private final String pluginIdentifier;

    private final String fileName;

    private final InternalViewDefinitionService viewDefinitionService;

    private final ViewDefinitionParser viewDefinitionParser;

    private final ViewExtension viewExtension;

    private Map<WindowComponentPattern, InternalRibbonGroup> addedGroups;

    public ViewRibbonModule(final String pluginIdentifier, final Resource xmlFile,
            final InternalViewDefinitionService viewDefinitionService, final ViewDefinitionParser viewDefinitionParser) {
        this.pluginIdentifier = pluginIdentifier;
        this.viewDefinitionService = viewDefinitionService;
        this.viewDefinitionParser = viewDefinitionParser;
        fileName = xmlFile.getFilename();
        try {
            viewExtension = viewDefinitionParser.getViewExtensionNode(xmlFile.getInputStream(), "ribbonExtension");
        } catch (IOException e) {
            throw ViewDefinitionParserException.forFile(fileName, e);
        } catch (ViewDefinitionParserNodeException e) {
            throw ViewDefinitionParserException.forFileAndNode(fileName, e);
        }
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        addedGroups = new HashMap<WindowComponentPattern, InternalRibbonGroup>();

        InternalViewDefinition viewDefinition = viewDefinitionService.getWithoutSession(viewExtension.getPluginName(),
                viewExtension.getViewName());
        if (viewDefinition == null) {
            throw new ModuleException(pluginIdentifier, "view", "reference to view which not exists");
        }

        try {

            for (Node groupNode : viewDefinitionParser.geElementChildren(viewExtension.getExtesionNode())) {

                InternalRibbonGroup group;
                try {
                    group = viewDefinitionParser.parseRibbonGroup(groupNode, viewDefinition);

                    group.setExtensionPluginIdentifier(pluginIdentifier);

                    WindowComponentPattern window = viewDefinition.getRootWindow();

                    window.getRibbon().addGroup(group);
                    addedGroups.put(window, group);
                } catch (ViewDefinitionParserNodeException e) {
                    throw ViewDefinitionParserException.forFileAndNode(fileName, e);
                }
            }
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, "view-ribbon-group", e);
        }
    }

    @Override
    public void disable() {
        for (Map.Entry<WindowComponentPattern, InternalRibbonGroup> addedGroupEntry : addedGroups.entrySet()) {
            addedGroupEntry.getKey().getRibbon().removeGroup(addedGroupEntry.getValue());
        }
    }

}
