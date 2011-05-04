package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
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
        String plugin = element.getAttributeValue("plugin");
        String view = element.getAttributeValue("view");
        String component = element.getAttributeValue("component");
        String eventName = element.getAttributeValue("event");
        String className = element.getAttributeValue("class");
        String method = element.getAttributeValue("method");
        Preconditions.checkNotNull(plugin, "Plugin '" + pluginIdentifier
                + "' - view listener extension error: plugin not defined");
        Preconditions.checkNotNull(view, "Plugin '" + pluginIdentifier + "' - view listener extension error: view not defined");
        Preconditions.checkNotNull(component, "Plugin '" + pluginIdentifier
                + "' - view listener extension error: component not defined");
        Preconditions.checkNotNull(eventName, "Plugin '" + pluginIdentifier
                + "' - view listener extension error: event not defined");
        Preconditions.checkNotNull(className, "Plugin '" + pluginIdentifier
                + "' - view listener extension error: class not defined");
        Preconditions.checkNotNull(method, "Plugin '" + pluginIdentifier
                + "' - view listener extension error: method not defined");

        HookDefinitionImpl hook = (HookDefinitionImpl) hookFactory.getHook(className, method, pluginIdentifier);
        ComponentCustomEvent event = new ComponentCustomEvent(eventName, hook.getObject(), method, pluginIdentifier);

        return new ViewListenerModule(viewDefinitionService, plugin, view, component, event);
    }

    @Override
    public String getIdentifier() {
        return "view-listener";
    }

}
