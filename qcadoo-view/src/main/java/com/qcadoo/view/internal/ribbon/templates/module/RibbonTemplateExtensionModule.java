package com.qcadoo.view.internal.ribbon.templates.module;

import java.util.LinkedList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.internal.ribbon.templates.RibbonTemplatesService;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;
import com.qcadoo.view.internal.ribbon.templates.model.TemplateRibbonGroup;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserException;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;

public class RibbonTemplateExtensionModule extends Module {

    private final RibbonTemplatesService ribbonTemplatesService;

    private final String fileName;

    private final String templatePlugin;

    private final String templateName;

    private final List<TemplateRibbonGroup> extensionGroups = new LinkedList<TemplateRibbonGroup>();

    public RibbonTemplateExtensionModule(final String pluginIdentifier, final Resource xmlFile,
            final ViewDefinitionParser parser, final RibbonTemplatesService ribbonTemplatesService) {
        fileName = xmlFile.getFilename();
        this.ribbonTemplatesService = ribbonTemplatesService;
        try {

            Node root = parser.getRootOfXmlDocument(xmlFile);
            parser.checkState("ribbonTemplateExtension".equals(root.getNodeName()), root,
                    "Wrong root node name '" + root.getNodeName() + "'");

            templatePlugin = parser.getStringAttribute(root, "templatePlugin");
            templateName = parser.getStringAttribute(root, "templateName");
            parser.checkState(templateName != null, root, "Ribbon template extension error: templateName not defined");

            for (Node groupNode : parser.geElementChildren(root)) {
                String groupName = parser.getStringAttribute(groupNode, "name");
                parser.checkState(groupName != null, groupNode, "Ribbon template error: group name not defined");
                TemplateRibbonGroup templateGroup = new TemplateRibbonGroup(groupName, pluginIdentifier);

                for (Node itemNode : parser.geElementChildren(groupNode)) {
                    templateGroup.addActionItem(parser.parseRibbonItem(itemNode, null));
                }

                extensionGroups.add(templateGroup);
            }

        } catch (ViewDefinitionParserNodeException e) {
            throw ViewDefinitionParserException.forFileAndNode(fileName, e);
        } catch (Exception e) {
            throw ViewDefinitionParserException.forFile(fileName, e);
        }
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        RibbonTemplate templateToExtend = getTemplateToExtend();
        for (TemplateRibbonGroup extensionGroup : extensionGroups) {
            templateToExtend.addTemplateGroup(extensionGroup);
        }
    }

    @Override
    public void disable() {
        RibbonTemplate templateToExtend = getTemplateToExtend();
        for (TemplateRibbonGroup extensionGroup : extensionGroups) {
            templateToExtend.removeTemplateGroup(extensionGroup);
        }
    }

    private RibbonTemplate getTemplateToExtend() {
        RibbonTemplate templateToExtend = ribbonTemplatesService.getTemplate(templatePlugin, templateName);
        if (templateToExtend == null) {
            throw ViewDefinitionParserException.forFile(fileName, "Ribbon template extension error: template '" + templatePlugin
                    + "." + templateName + "' not found", null);
        }
        return templateToExtend;
    }

}
