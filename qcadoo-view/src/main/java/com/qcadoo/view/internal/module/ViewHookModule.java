package com.qcadoo.view.internal.module;

import com.google.common.base.Preconditions;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.HookDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;

public class ViewHookModule extends Module {

    private final InternalViewDefinitionService viewDefinitionService;

    private final String extendsViewPlugin;

    private final String extendsViewName;

    private final InternalViewDefinition.HookType hookType;

    private final HookDefinition hook;

    private final String pluginIdentifier;

    public ViewHookModule(final String pluginIdentifier, final InternalViewDefinitionService viewDefinitionService,
            final String extendsViewPlugin, final String extendsViewName, final InternalViewDefinition.HookType hookType,
            final HookDefinition hook) {
        this.pluginIdentifier = pluginIdentifier;
        this.viewDefinitionService = viewDefinitionService;
        this.extendsViewPlugin = extendsViewPlugin;
        this.extendsViewName = extendsViewName;
        this.hookType = hookType;
        this.hook = hook;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        try {
            getViewDefinition().addHook(hookType, hook);
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, "view-hook", e);
        }
    }

    @Override
    public void disable() {
        getViewDefinition().removeHook(hookType, hook);
    }

    private InternalViewDefinition getViewDefinition() {
        InternalViewDefinition extendsView = (InternalViewDefinition) viewDefinitionService.getWithoutSession(extendsViewPlugin,
                extendsViewName);
        Preconditions.checkNotNull(extendsView, "extension referes to view which not exists (" + extendsViewPlugin + " - "
                + extendsViewName + ")");
        return extendsView;
    }

}
