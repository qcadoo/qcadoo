package com.qcadoo.view.internal.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.components.window.WindowComponentPattern;
import com.qcadoo.view.internal.components.window.WindowTabComponentPattern;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserException;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;
import com.qcadoo.view.internal.xml.ViewExtension;

public class ViewTabModule extends Module {

    private final String pluginIdentifier;

    private final InternalViewDefinitionService viewDefinitionService;

    private final ViewDefinitionParser viewDefinitionParser;

    private final ViewExtension viewExtension;

    private final String fileName;

    private Map<WindowComponentPattern, ComponentPattern> addedTabs;

    public ViewTabModule(final String pluginIdentifier, final Resource xmlFile,
            final InternalViewDefinitionService viewDefinitionService, final ViewDefinitionParser viewDefinitionParser) {
        this.pluginIdentifier = pluginIdentifier;
        this.viewDefinitionService = viewDefinitionService;
        this.viewDefinitionParser = viewDefinitionParser;
        fileName = xmlFile.getFilename();
        try {
            viewExtension = viewDefinitionParser.getViewExtensionNode(xmlFile.getInputStream(), "windowTabExtension");
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
        addedTabs = new HashMap<WindowComponentPattern, ComponentPattern>();

        InternalViewDefinition viewDefinition = viewDefinitionService.getWithoutSession(viewExtension.getPluginName(),
                viewExtension.getViewName());
        if (viewDefinition == null) {
            throw new ModuleException(pluginIdentifier, "view", "reference to view which not exists");
        }

        try {
            for (Node tabNode : viewDefinitionParser.geElementChildren(viewExtension.getExtesionNode())) {

                WindowComponentPattern window = viewDefinition.getRootWindow();

                ComponentDefinition tabDefinition = viewDefinitionParser.getComponentDefinition(tabNode, window, viewDefinition);
                tabDefinition.setExtensionPluginIdentifier(pluginIdentifier);

                ComponentPattern tabPattern = new WindowTabComponentPattern(tabDefinition);

                try {
                    tabPattern.parse(tabNode, viewDefinitionParser);
                } catch (ViewDefinitionParserNodeException e) {
                    throw ViewDefinitionParserException.forFileAndNode(fileName, e);
                }

                window.addChild(tabPattern);
                addedTabs.put(window, tabPattern);

                tabPattern.initializeAll();
                tabPattern.registerViews(viewDefinitionService);
            }
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, "view-tab", e);
        }
    }

    @Override
    public void disable() {
        for (Map.Entry<WindowComponentPattern, ComponentPattern> addedGroupEntry : addedTabs.entrySet()) {
            addedGroupEntry.getValue().unregisterComponent(viewDefinitionService);
            addedGroupEntry.getKey().removeChild(addedGroupEntry.getValue().getName());
        }
    }

}
