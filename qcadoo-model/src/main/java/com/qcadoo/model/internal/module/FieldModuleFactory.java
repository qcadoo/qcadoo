/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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

package com.qcadoo.model.internal.module;

import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.model.internal.api.InternalDataDefinitionService;
import com.qcadoo.plugin.api.ModuleFactory;

public class FieldModuleFactory extends ModuleFactory<FieldModule> {

    @Autowired
    private ModelXmlHolder modelXmlHolder;

    @Autowired
    private InternalDataDefinitionService dataDefinitionService;

    @Override
    @SuppressWarnings("unchecked")
    public FieldModule parse(final String pluginIdentifier, final Element element) {
        String targetPluginIdentifier = element.getAttributeValue("plugin");
        String targetModelName = element.getAttributeValue("model");

        if (targetPluginIdentifier == null) {
            throw new IllegalStateException("Missing plugin attribute of field module");
        }

        if (targetModelName == null) {
            throw new IllegalStateException("Missing model attribute of field module");
        }

        List<Element> elements = element.getChildren();

        if (elements.size() < 1) {
            throw new IllegalStateException("Missing content of field module");
        } else if (elements.size() > 1) {
            throw new IllegalStateException("Only one field can be defined in single field module");
        }

        String fieldName = elements.get(0).getAttributeValue("name");

        Element field = elements.get(0);
        field.setAttribute("required", "false");

        modelXmlHolder.addField(targetPluginIdentifier, targetModelName, field);

        return new FieldModule(targetPluginIdentifier, targetModelName, fieldName, dataDefinitionService);
    }

    @Override
    public String getIdentifier() {
        return "field";
    }

}
