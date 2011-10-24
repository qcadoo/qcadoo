/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
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

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.api.ComponentCustomEvent;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.ContainerPattern;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonActionItem;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;

public interface ViewDefinitionParser {

    ComponentOption parseOption(Node option);

    ComponentPattern parseComponent(Node componentNode, ContainerPattern parent) throws ViewDefinitionParserNodeException;

    ComponentDefinition getComponentDefinition(Node componentNode, ContainerPattern parent, ViewDefinition viewDefinition);

    ComponentCustomEvent parseCustomEvent(Node listenerNode) throws ViewDefinitionParserNodeException;

    String getStringAttribute(Node groupNode, String string);

    String getStringNodeContent(Node node);

    Boolean getBooleanAttribute(Node node, String name, boolean defaultValue);

    List<Node> geElementChildren(Node node);

    Node getRootOfXmlDocument(Resource xmlFile);

    InternalViewDefinition parseViewXml(Resource viewXml, String pluginIdentifier);

    ViewExtension getViewExtensionNode(InputStream resource, String tagType) throws ViewDefinitionParserNodeException;

    InternalRibbon parseRibbon(Node groupNode, ViewDefinition viewDefinition) throws ViewDefinitionParserNodeException;

    InternalRibbonGroup parseRibbonGroup(Node groupNode, ViewDefinition viewDefinition) throws ViewDefinitionParserNodeException;

    InternalRibbonActionItem parseRibbonItem(Node itemNode, ViewDefinition viewDefinition)
            throws ViewDefinitionParserNodeException;

    void checkState(boolean state, Node node, String message) throws ViewDefinitionParserNodeException;

}
