/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.4
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
package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.ComponentCustomEvent;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.hooks.HookDefinitionImpl;
import com.qcadoo.view.internal.hooks.HookFactory;

public class ViewListenerModuleFactory extends ModuleFactory<ViewListenerModule> {

    @Autowired
    private HookFactory hookFactory;

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Override
    protected ViewListenerModule parseElement(final String pluginIdentifier, final Element element) {
        String plugin = getRequiredAttribute(element, "plugin");
        String view = getRequiredAttribute(element, "view");
        String component = getRequiredAttribute(element, "component");
        String eventName = getRequiredAttribute(element, "event");
        String className = getRequiredAttribute(element, "class");
        String method = getRequiredAttribute(element, "method");
        HookDefinitionImpl hook = (HookDefinitionImpl) hookFactory.getHook(className, method, pluginIdentifier);
        ComponentCustomEvent event = new ComponentCustomEvent(eventName, hook.getObject(), method, pluginIdentifier);

        return new ViewListenerModule(pluginIdentifier, viewDefinitionService, plugin, view, component, event);
    }

    @Override
    public String getIdentifier() {
        return "view-listener";
    }

}
