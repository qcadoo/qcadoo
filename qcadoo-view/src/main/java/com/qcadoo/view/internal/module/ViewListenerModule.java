package com.qcadoo.view.internal.module;

import com.google.common.base.Preconditions;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.api.ComponentCustomEvent;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;

public class ViewListenerModule extends Module {

    private final InternalViewDefinitionService viewDefinitionService;

    private final String extendsViewPlugin;

    private final String extendsViewName;

    private final String extendsComponentName;

    private final ComponentCustomEvent event;

    private final String pluginIdentifier;

    public ViewListenerModule(final String pluginIdentifier, final InternalViewDefinitionService viewDefinitionService,
            final String extendsViewPlugin, final String extendsViewName, final String extendsComponentName,
            final ComponentCustomEvent event) {
        this.pluginIdentifier = pluginIdentifier;
        this.viewDefinitionService = viewDefinitionService;
        this.extendsViewPlugin = extendsViewPlugin;
        this.extendsViewName = extendsViewName;
        this.extendsComponentName = extendsComponentName;
        this.event = event;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        try {
            getComponent().addCustomEvent(event);
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, "view-listener", e);
        }
    }

    @Override
    public void disable() {
        getComponent().removeCustomEvent(event);
    }

    private ComponentPattern getComponent() {
        InternalViewDefinition extendsView = (InternalViewDefinition) viewDefinitionService.getWithoutSession(extendsViewPlugin,
                extendsViewName);
        Preconditions.checkNotNull(extendsView, "extension referes to view which not exists (" + extendsViewPlugin + " - "
                + extendsViewName + ")");
        ComponentPattern component = extendsView.getComponentByReference(extendsComponentName);
        Preconditions.checkNotNull(component, "component '" + extendsComponentName + "' not exists in view " + extendsViewPlugin
                + " - " + extendsViewName + ")");
        return component;
    }

}
