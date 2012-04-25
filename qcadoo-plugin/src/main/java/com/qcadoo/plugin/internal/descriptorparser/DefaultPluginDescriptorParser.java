/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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
package com.qcadoo.plugin.internal.descriptorparser;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.plugin.api.PluginState;
import com.qcadoo.plugin.internal.DefaultPlugin.Builder;
import com.qcadoo.plugin.internal.JarEntryResource;
import com.qcadoo.plugin.internal.PluginException;
import com.qcadoo.plugin.internal.api.InternalPlugin;
import com.qcadoo.plugin.internal.api.ModuleFactoryAccessor;
import com.qcadoo.plugin.internal.api.PluginDescriptorParser;
import com.qcadoo.plugin.internal.api.PluginDescriptorResolver;

@Service
public class DefaultPluginDescriptorParser implements PluginDescriptorParser {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPluginDescriptorParser.class);

    @Autowired
    private ModuleFactoryAccessor moduleFactoryAccessor;

    @Autowired
    private PluginDescriptorResolver pluginDescriptorResolver;

    private DocumentBuilder documentBuilder;

    public DefaultPluginDescriptorParser() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                URL url = new URL("http://www.qcadoo.com");
                url.openConnection();
                factory.setValidating(true);
            } catch (UnknownHostException e) {
                factory.setValidating(false);
            } catch (IOException e) {
                factory.setValidating(false);
            }
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

            documentBuilder = factory.newDocumentBuilder();

            documentBuilder.setErrorHandler(new com.qcadoo.plugin.api.errorhandler.ValidationErrorHandler());

        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Error while parsing plugin xml schema", e);
        }
    }

    @Override
    public InternalPlugin parse(final Resource resource, final boolean ignoreModules) {
        try {
            return parse(new JarEntryResource(resource), ignoreModules);

        } catch (IOException e) {
            throw new PluginException(e.getMessage(), e);
        }
    }

    @Override
    public InternalPlugin parse(final JarEntryResource resource, final boolean ignoreModules) {
        try {
            LOG.info("Parsing descriptor for:" + resource);

            Document document = documentBuilder.parse(resource.getInputStream());

            Node root = document.getDocumentElement();

            Builder pluginBuilder = parsePluginNode(root, ignoreModules);

            InternalPlugin plugin = pluginBuilder.withFileName(resource.getJarFileName()).build();

            LOG.info("Parse complete");

            return plugin;

        } catch (SAXException e) {
            throw new PluginException(e.getMessage(), e);
        } catch (IOException e) {
            throw new PluginException(e.getMessage(), e);
        } catch (Exception e) {
            throw new PluginException(e.getMessage(), e);
        }
    }

    @Override
    public Set<InternalPlugin> loadPlugins() {
        Map<String, InternalPlugin> loadedplugins = new HashMap<String, InternalPlugin>();
        Resource[] resources = pluginDescriptorResolver.getDescriptors();
        for (Resource resource : resources) {
            InternalPlugin plugin = parse(resource, false);

            if (loadedplugins.containsKey(plugin.getIdentifier())) {
                throw new PluginException("Duplicated plugin identifier: " + plugin.getIdentifier());
            }

            loadedplugins.put(plugin.getIdentifier(), plugin);
        }
        return new HashSet<InternalPlugin>(loadedplugins.values());
    }

    @Override
    public Set<InternalPlugin> getTemporaryPlugins() {
        JarEntryResource[] resources = pluginDescriptorResolver.getTemporaryDescriptors();
        Set<InternalPlugin> plugins = new HashSet<InternalPlugin>();
        for (JarEntryResource resource : resources) {
            InternalPlugin plugin = parse(resource, true);
            plugin.changeStateTo(PluginState.TEMPORARY);
            plugins.add(plugin);
        }
        return plugins;
    }

    private Builder parsePluginNode(final Node pluginNode, final boolean ignoreModules) {
        Preconditions.checkState("plugin".equals(pluginNode.getNodeName()), "Wrong plugin description root tag");

        String pluginIdentifier = getStringAttribute(pluginNode, "plugin");
        checkNotNull(pluginIdentifier, "No plugin identifier");

        LOG.info("Parsing plugin " + pluginIdentifier);

        Builder pluginBuilder = new Builder(pluginIdentifier, moduleFactoryAccessor.getModuleFactories());

        String pluginVersionStr = getStringAttribute(pluginNode, "version");
        checkNotNull(pluginVersionStr, "No plugin version");
        pluginBuilder.withVersion(pluginVersionStr);

        String isSystemPluginStr = getStringAttribute(pluginNode, "system");
        if (isSystemPluginStr != null && Boolean.parseBoolean(isSystemPluginStr)) {
            pluginBuilder.asSystem();
        }

        for (Node child : getChildNodes(pluginNode)) {
            if ("information".equals(child.getNodeName())) {
                addPluginInformation(child, pluginBuilder);
            } else if ("dependencies".equals(child.getNodeName())) {
                addDependenciesInformation(child, pluginBuilder);
            } else if ("modules".equals(child.getNodeName())) {
                if (!ignoreModules) {
                    addModules(child, pluginBuilder, pluginIdentifier);
                }
            } else {
                throw new IllegalStateException("Wrong plugin tag: " + child.getNodeName());
            }
        }

        return pluginBuilder;
    }

    private void addPluginInformation(final Node informationsNode, final Builder pluginBuilder) {
        for (Node child : getChildNodes(informationsNode)) {
            if ("name".equals(child.getNodeName())) {
                pluginBuilder.withName(getTextContent(child));
            } else if ("description".equals(child.getNodeName())) {
                pluginBuilder.withDescription(getTextContent(child));
            } else if ("vendor".equals(child.getNodeName())) {
                addPluginVendorInformation(child, pluginBuilder);
            } else {
                throw new IllegalStateException("Wrong plugin information tag: " + child.getNodeName());
            }
        }
    }

    private void addPluginVendorInformation(final Node vendorInformationsNode, final Builder pluginBuilder) {
        for (Node child : getChildNodes(vendorInformationsNode)) {
            if ("name".equals(child.getNodeName())) {
                pluginBuilder.withVendor(getTextContent(child));
            } else if ("url".equals(child.getNodeName())) {
                pluginBuilder.withVendorUrl(getTextContent(child));
            } else {
                throw new IllegalStateException("Wrong plugin vendor tag: " + child.getNodeName());
            }
        }
    }

    private void addDependenciesInformation(final Node dependenciesNode, final Builder pluginBuilder) {
        for (Node child : getChildNodes(dependenciesNode)) {
            if ("dependency".equals(child.getNodeName())) {
                addDependencyInformation(child, pluginBuilder);
            } else {
                throw new IllegalStateException("Wrong plugin dependency tag: " + child.getNodeName());
            }
        }
    }

    private void addDependencyInformation(final Node dependencyNode, final Builder pluginBuilder) {
        String dependencyPluginIdentifier = null;
        String dependencyPluginVersion = null;

        for (Node child : getChildNodes(dependencyNode)) {
            if ("plugin".equals(child.getNodeName())) {
                dependencyPluginIdentifier = getTextContent(child);
            } else if ("version".equals(child.getNodeName())) {
                dependencyPluginVersion = getTextContent(child);
            } else {
                throw new IllegalStateException("Wrong plugin dependency tag: " + child.getNodeName());
            }
        }

        checkNotNull(dependencyPluginIdentifier, "No plugin dependency identifier");
        pluginBuilder.withDependency(dependencyPluginIdentifier, dependencyPluginVersion);
    }

    private void addModules(final Node modulesNode, final Builder pluginBuilder, final String pluginIdentifier) {
        for (Node child : getChildNodes(modulesNode)) {
            ModuleFactory<?> moduleFactory = moduleFactoryAccessor.getModuleFactory(child.getLocalName());
            LOG.info("Parsing module " + child.getLocalName() + " for plugin " + pluginIdentifier);
            Module module = moduleFactory.parse(pluginIdentifier, convertNodeToJdomElement(child));
            checkNotNull(module, "Module for " + child.getLocalName() + " is null");
            pluginBuilder.withModule(moduleFactory, module);
        }
    }

    private Element convertNodeToJdomElement(final Node child) {
        return new DOMBuilder().build((org.w3c.dom.Element) child);
    }

    private List<Node> getChildNodes(final Node node) {
        List<Node> result = new LinkedList<Node>();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            result.add(child);
        }
        return result;
    }

    private String getTextContent(final Node node) {
        String result = node.getTextContent();
        if (result != null) {
            result = result.trim();
            if (result.isEmpty()) {
                return null;
            }
            return result;
        }
        return null;
    }

    private String getStringAttribute(final Node node, final String name) {
        if (node != null && node.getAttributes() != null) {
            Node attribute = node.getAttributes().getNamedItem(name);
            if (attribute != null) {
                return attribute.getNodeValue();
            }
        }
        return null;
    }

    public void setModuleFactoryAccessor(final ModuleFactoryAccessor moduleFactoryAccessor) {
        this.moduleFactoryAccessor = moduleFactoryAccessor;
    }

    public void setPluginDescriptorResolver(final PluginDescriptorResolver pluginDescriptorResolver) {
        this.pluginDescriptorResolver = pluginDescriptorResolver;
    }

}
