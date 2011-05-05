/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */

package com.qcadoo.view.internal.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.HookDefinition;
import com.qcadoo.view.internal.api.ComponentCustomEvent;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.ContainerPattern;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.hooks.HookDefinitionImpl;
import com.qcadoo.view.internal.hooks.HookFactory;
import com.qcadoo.view.internal.internal.ViewComponentsResolverImpl;
import com.qcadoo.view.internal.internal.ViewDefinitionImpl;
import com.qcadoo.view.internal.patterns.AbstractComponentPattern;
import com.qcadoo.view.internal.ribbon.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.RibbonUtils;

@Service
public final class ViewDefinitionParserImpl implements ViewDefinitionParser {

    private static final Logger LOG = LoggerFactory.getLogger(ViewDefinitionParserImpl.class);

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Autowired
    private ViewComponentsResolverImpl viewComponentsResolver;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private SecurityRolesService securityRolesService;

    @Autowired
    private HookFactory hookFactory;

    private int currentIndexOrder;

    @Override
    public InternalViewDefinition parseViewXml(final Resource viewXml, final String pluginIdentifier) {
        try {
            return parse(viewXml.getInputStream(), pluginIdentifier);
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading view resource", e);
        }
    }

    private InternalViewDefinition parse(final InputStream dataDefinitionInputStream, final String pluginIdentifier) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(dataDefinitionInputStream);

            Node root = document.getDocumentElement();

            Preconditions.checkState("view".equals(root.getNodeName()), "Wrong root node '" + root.getNodeName() + "'");

            return parseViewDefinition(root, pluginIdentifier);

        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private InternalViewDefinition parseViewDefinition(final Node viewNode, final String pluginIdentifier) {
        currentIndexOrder = 1;
        String name = getStringAttribute(viewNode, "name");
        Preconditions.checkState(name != null && !"".equals(name.trim()), "Name attribute cannot be empty");

        LOG.info("Reading view " + name + " for plugin " + pluginIdentifier);

        boolean menuAccessible = getBooleanAttribute(viewNode, "menuAccessible", false);

        String windowWidthStr = getStringAttribute(viewNode, "windowWidth");
        String windowHeightStr = getStringAttribute(viewNode, "windowHeight");
        Integer windowWidth = null;
        Integer windowHeight = null;
        if (windowWidthStr != null) {
            windowWidth = Integer.parseInt(windowWidthStr);
        }
        if (windowHeightStr != null) {
            windowHeight = Integer.parseInt(windowHeightStr);
        }

        String authorizationRole = getStringAttribute(viewNode, "defaultAuthorizationRole");
        SecurityRole role = null;
        if (authorizationRole != null) {
            role = securityRolesService.getRoleByIdentifier(authorizationRole);
            if (role == null) {
                throw new IllegalStateException("no such role: '" + authorizationRole + "'");
            }
        }

        DataDefinition dataDefinition = null;

        if (getStringAttribute(viewNode, "modelName") != null) {
            String modelPluginIdentifier = getStringAttribute(viewNode, "modelPlugin") != null ? getStringAttribute(viewNode,
                    "modelPlugin") : pluginIdentifier;
            dataDefinition = dataDefinitionService.get(modelPluginIdentifier, getStringAttribute(viewNode, "modelName"));
        }

        ViewDefinitionImpl viewDefinition = new ViewDefinitionImpl(name, pluginIdentifier, role, dataDefinition, menuAccessible,
                translationService);

        viewDefinition.setWindowDimmension(windowWidth, windowHeight);

        ComponentPattern root = null;

        NodeList childNodes = viewNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if ("component".equals(child.getNodeName())) {
                root = parseComponent(child, viewDefinition, null, pluginIdentifier);
            } else if ("beforeInitalize".equals(child.getNodeName())) {
                viewDefinition.addPreInitializeHook(parseHook(child));
            } else if ("afterInitialize".equals(child.getNodeName())) {
                viewDefinition.addPostInitializeHook(parseHook(child));
            } else if ("beforeRender".equals(child.getNodeName())) {
                viewDefinition.addPreRenderHook(parseHook(child));
            }
        }

        viewDefinition.addComponentPattern(root);

        viewDefinition.initialize();

        viewDefinition.registerViews(viewDefinitionService);

        return viewDefinition;
    }

    @Override
    public Boolean getBooleanAttribute(final Node node, final String name, final boolean defaultValue) {
        Node attribute = getAttribute(node, name);
        if (attribute != null) {
            return Boolean.valueOf(attribute.getNodeValue());
        } else {
            return defaultValue;
        }
    }

    @Override
    public String getStringAttribute(final Node node, final String name) {
        Node attribute = getAttribute(node, name);
        if (attribute != null) {
            return attribute.getNodeValue();
        } else {
            return null;
        }
    }

    @Override
    public String getStringNodeContent(final Node node) {
        NodeList childNodes = node.getChildNodes();
        StringBuilder contentSB = new StringBuilder();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE) {
                contentSB.append(child.getNodeValue());
            }
        }
        return contentSB.toString().trim();
    }

    private Node getAttribute(final Node node, final String name) {
        if (node != null && node.getAttributes() != null) {
            return node.getAttributes().getNamedItem(name);
        } else {
            return null;
        }
    }

    @Override
    public ComponentOption parseOption(final Node optionNode) {
        Map<String, String> attributes = new HashMap<String, String>();

        NamedNodeMap attributesNodes = optionNode.getAttributes();

        for (int i = 0; i < attributesNodes.getLength(); i++) {
            attributes.put(attributesNodes.item(i).getNodeName(), attributesNodes.item(i).getNodeValue());
        }
        return new ComponentOption(getStringAttribute(optionNode, "type"), attributes);
    }

    public ComponentPattern parseComponent(final Node componentNode, final ViewDefinition viewDefinition,
            final ContainerPattern parent, final String pluginIdentifier) {
        String type = getStringAttribute(componentNode, "type");

        if (parent == null && !("window".equals(type) || "tabWindow".equals(type))) {
            throw new IllegalStateException("Unsupported component: " + type);
        }

        ComponentPattern component = viewComponentsResolver.getComponentInstance(type,
                getComponentDefinition(componentNode, parent, viewDefinition));

        component.parse(componentNode, this);

        return component;
    }

    @Override
    public ComponentDefinition getComponentDefinition(final Node componentNode, final ContainerPattern parent,
            final ViewDefinition viewDefinition) {
        String name = getStringAttribute(componentNode, "name");
        String fieldPath = getStringAttribute(componentNode, "field");
        String sourceFieldPath = getStringAttribute(componentNode, "source");
        String plugin = getStringAttribute(componentNode, "plugin");
        String model = getStringAttribute(componentNode, "model");

        DataDefinition customDataDefinition = null;

        if (model != null) {
            String modelPluginIdentifier = plugin != null ? plugin : viewDefinition.getPluginIdentifier();
            customDataDefinition = dataDefinitionService.get(modelPluginIdentifier, model);
        }

        ComponentDefinition componentDefinition = new ComponentDefinition();
        componentDefinition.setName(name);
        componentDefinition.setFieldPath(fieldPath);
        componentDefinition.setSourceFieldPath(sourceFieldPath);
        componentDefinition.setParent(parent);
        componentDefinition.setTranslationService(translationService);
        componentDefinition.setViewDefinition(viewDefinition);
        componentDefinition.setReference(getStringAttribute(componentNode, "reference"));
        componentDefinition.setDefaultEnabled(getBooleanAttribute(componentNode, "defaultEnabled", true));
        componentDefinition.setDefaultVisible(getBooleanAttribute(componentNode, "defaultVisible", true));
        componentDefinition.setHasLabel(getBooleanAttribute(componentNode, "hasLabel", true));
        componentDefinition.setHasDescription(getBooleanAttribute(componentNode, "hasDescription", false));
        componentDefinition.setDataDefinition(customDataDefinition);

        return componentDefinition;
    }

    @Override
    public ComponentPattern parseComponent(final Node componentNode, final ContainerPattern parent) {
        return parseComponent(componentNode, ((AbstractComponentPattern) parent).getViewDefinition(), parent,
                ((AbstractComponentPattern) parent).getViewDefinition().getPluginIdentifier());
    }

    @Override
    public ComponentCustomEvent parseCustomEvent(final Node listenerNode) {
        HookDefinitionImpl hookDefinition = (HookDefinitionImpl) parseHook(listenerNode);
        return new ComponentCustomEvent(getStringAttribute(listenerNode, "event"), hookDefinition.getObject(),
                hookDefinition.getMethod(), null);
    }

    public HookDefinition parseHook(final Node hookNode) {
        String fullyQualifiedClassName = getStringAttribute(hookNode, "class");
        String methodName = getStringAttribute(hookNode, "method");
        Preconditions.checkState(StringUtils.hasText(fullyQualifiedClassName), "Hook class name is required");
        Preconditions.checkState(StringUtils.hasText(methodName), "Hook method name is required");
        return hookFactory.getHook(fullyQualifiedClassName, methodName, null);
    }

    public int getCurrentIndexOrder() {
        return currentIndexOrder++;
    }

    @Override
    public List<Node> geElementChildren(final Node node) {
        List<Node> result = new LinkedList<Node>();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.add(child);
            }
        }
        return result;
    }

    @Override
    public ViewExtension getViewExtensionNode(final InputStream resource, final String tagType) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(resource);

            Node root = document.getDocumentElement();

            Preconditions.checkState(root.getNodeName().equals(tagType), "Wrong root node name: " + root.getNodeName());

            String plugin = getStringAttribute(root, "plugin");
            String view = getStringAttribute(root, "view");

            Preconditions.checkNotNull(plugin, "View extension error: plugin not defined");
            Preconditions.checkNotNull(view, "View extension error: view not defined");

            return new ViewExtension(plugin, view, root);

        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public InternalRibbonGroup parseRibbonGroup(final Node groupNode, final ViewDefinition viewDefinition) {
        return RibbonUtils.getInstance().parseRibbonGroup(groupNode, this, viewDefinition);
    }
}
