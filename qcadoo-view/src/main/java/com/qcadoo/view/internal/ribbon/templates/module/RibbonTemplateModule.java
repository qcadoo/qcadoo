package com.qcadoo.view.internal.ribbon.templates.module;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.internal.ribbon.templates.RibbonTemplatesService;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;
import com.qcadoo.view.internal.ribbon.templates.model.TemplateRibbonGroup;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserException;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;

public class RibbonTemplateModule extends Module {

    final String pluginIdentifier;

    final String templateName;

    final RibbonTemplate template;

    final RibbonTemplatesService ribbonTemplatesService;

    public RibbonTemplateModule(final String pluginIdentifier, final Resource xmlFile, final ViewDefinitionParser parser,
            final RibbonTemplatesService ribbonTemplatesService) {
        this.pluginIdentifier = pluginIdentifier;
        this.ribbonTemplatesService = ribbonTemplatesService;

        String fileName = xmlFile.getFilename();
        try {

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile.getInputStream());

            Node root = document.getDocumentElement();
            parser.checkState("ribbonTemplate".equals(root.getNodeName()), root, "Wrong root node name '" + root.getNodeName()
                    + "'");

            templateName = parser.getStringAttribute(root, "name");
            parser.checkState(templateName != null, root, "Ribbon template error: name not defined");

            template = new RibbonTemplate(pluginIdentifier, templateName);

            for (Node groupNode : parser.geElementChildren(root)) {
                String groupName = parser.getStringAttribute(groupNode, "name");
                parser.checkState(groupName != null, groupNode, "Ribbon template error: group name not defined");
                TemplateRibbonGroup templateGroup = new TemplateRibbonGroup(groupName);

                for (Node itemNode : parser.geElementChildren(groupNode)) {
                    templateGroup.addActionItem(parser.parseRibbonItem(itemNode, null));
                }

                template.addTemplateGroup(templateGroup);
            }

        } catch (ParserConfigurationException e) {
            throw ViewDefinitionParserException.forFile(fileName, e);
        } catch (SAXException e) {
            throw ViewDefinitionParserException.forFile(fileName, e);
        } catch (IOException e) {
            throw ViewDefinitionParserException.forFile(fileName, e);
        } catch (ViewDefinitionParserNodeException e) {
            throw ViewDefinitionParserException.forFileAndNode(fileName, e);
        }
    }
}
