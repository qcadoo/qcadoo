package com.qcadoo.view.internal.hooks;

import java.util.Locale;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import com.qcadoo.model.internal.hooks.HookInitializationException;
import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.view.internal.api.ViewDefinition;

public class ViewConstructionHook extends AbstractViewHookDefinition {

    public ViewConstructionHook(final String className, final String methodName, final String pluginIdentifier,
            final ApplicationContext applicationContext) throws HookInitializationException {
        super(className, methodName, pluginIdentifier, applicationContext);
    }

    @Override
    protected Class<?>[] getParameterTypes() {
        return new Class<?>[] { ViewDefinition.class, JSONObject.class, Locale.class };
    }

    public void callWithJSONObject(final ViewDefinition viewDefinition, final JSONObject object, final Locale locale) {
        if (getPluginIdentifier() == null || PluginUtils.isEnabled(getPluginIdentifier())) {
            performCall(viewDefinition, object, locale);
        }
    }
}
