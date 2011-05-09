package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.HookDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.hooks.HookFactory;

public class ViewHookModuleFactory extends ModuleFactory<ViewHookModule> {

    @Autowired
    private HookFactory hookFactory;

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Override
    public ViewHookModule parse(final String pluginIdentifier, final Element element) {
        String plugin = getRequiredAttribute(element, "plugin");
        String view = getRequiredAttribute(element, "view");
        String hookTypeStr = getRequiredAttribute(element, "type");
        String className = getRequiredAttribute(element, "class");
        String method = getRequiredAttribute(element, "method");

        HookDefinition hook = hookFactory.getHook(className, method, pluginIdentifier);

        InternalViewDefinition.HookType hookType;
        if ("afterInitialize".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.POST_INITIALIZE;
        } else if ("beforeInitalize".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.PRE_INITIALIZE;
        } else if ("beforeRender".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.PRE_RENDER;
        } else {
            throw new IllegalStateException("Unknow view extension hook type: " + hookTypeStr);
        }

        return new ViewHookModule(viewDefinitionService, plugin, view, hookType, hook);
    }

    @Override
    public String getIdentifier() {
        return "view-hook";
    }

}
