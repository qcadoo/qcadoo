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
    public ViewListenerModule parse(final String pluginIdentifier, final Element element) {
        String plugin = getRequiredAttribute(element, "plugin");
        String view = getRequiredAttribute(element, "view");
        String component = getRequiredAttribute(element, "component");
        String eventName = getRequiredAttribute(element, "event");
        String className = getRequiredAttribute(element, "class");
        String method = getRequiredAttribute(element, "method");

        HookDefinitionImpl hook = (HookDefinitionImpl) hookFactory.getHook(className, method, pluginIdentifier);
        ComponentCustomEvent event = new ComponentCustomEvent(eventName, hook.getObject(), method, pluginIdentifier);

        return new ViewListenerModule(viewDefinitionService, plugin, view, component, event);
    }

    @Override
    public String getIdentifier() {
        return "view-listener";
    }

}
