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

public class HookModuleFactory implements ModuleFactory<HookModule> {

    @Autowired
    private ModelXmlHolder modelXmlHolder;

    @Autowired
    private InternalDataDefinitionService dataDefinitionService;

    @Override
    public void init() {
        // empty
    }

    @Override
    @SuppressWarnings("unchecked")
    public HookModule parse(final String pluginIdentifier, final Element element) {
        String targetPluginIdentifier = element.getAttributeValue("plugin");
        String targetModelName = element.getAttributeValue("model");

        if (targetPluginIdentifier == null) {
            throw new IllegalStateException("Missing plugin attribute of hook module");
        }

        if (targetModelName == null) {
            throw new IllegalStateException("Missing model attribute of hook module");
        }

        List<Element> elements = element.getChildren();

        if (elements.size() < 1) {
            throw new IllegalStateException("Missing content of hook module");
        } else if (elements.size() > 1) {
            throw new IllegalStateException("Only one hook can be defined in single hook module");
        }

        String hookType = elements.get(0).getName();
        String hookClass = elements.get(0).getAttributeValue("class");
        String hookMethod = elements.get(0).getAttributeValue("method");

        modelXmlHolder.addHook(targetPluginIdentifier, targetModelName, elements.get(0));

        return new HookModule(targetPluginIdentifier, targetModelName, hookType, hookClass, hookMethod, dataDefinitionService);
    }

    @Override
    public String getIdentifier() {
        return "hook";
    }

}
