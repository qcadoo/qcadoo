package com.qcadoo.view.internal.internal;

import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.hooks.AbstractViewHookDefinition;
import com.qcadoo.view.internal.hooks.HookType;
import com.qcadoo.view.internal.hooks.ViewConstructionHook;
import com.qcadoo.view.internal.hooks.ViewLifecycleHook;

public class ViewHooksHolder {

    private final List<ViewConstructionHook> constructionHooks = Lists.newArrayList();

    private final Multimap<HookType, ViewLifecycleHook> lifecycleHooks = ArrayListMultimap.create();

    public void addHook(final AbstractViewHookDefinition viewHook) {
        if (viewHook instanceof ViewLifecycleHook) {
            addLifecycleHook((ViewLifecycleHook) viewHook);
        } else if (viewHook instanceof ViewConstructionHook) {
            addConstructionHook((ViewConstructionHook) viewHook);
        } else {
            throw new IllegalArgumentException("Unsupported hook: " + viewHook);
        }
    }

    private void addLifecycleHook(final ViewLifecycleHook lifecycleHook) {
        HookType type = lifecycleHook.getType();
        lifecycleHooks.put(type, lifecycleHook);
    }

    private void addConstructionHook(final ViewConstructionHook postConstructHook) {
        addPostConstructHook(postConstructHook);
    }

    public void removeHook(final AbstractViewHookDefinition viewHook) {
        if (viewHook instanceof ViewLifecycleHook) {
            removeLifecycleHook((ViewLifecycleHook) viewHook);
        } else if (viewHook instanceof ViewConstructionHook) {
            removeConstructionHook((ViewConstructionHook) viewHook);
        } else {
            throw new IllegalArgumentException("Unsupported hook: " + viewHook);
        }
    }

    private void removeLifecycleHook(final ViewLifecycleHook lifecycleHook) {
        HookType type = lifecycleHook.getType();
        lifecycleHooks.remove(type, lifecycleHook);
    }

    private void removeConstructionHook(final ViewConstructionHook constructionHook) {
        constructionHooks.remove(constructionHook);
    }

    private void addPostConstructHook(final ViewConstructionHook viewHookDefinition) {
        constructionHooks.add(viewHookDefinition);
    }

    public void callLifecycleHooks(final HookType type, final ViewDefinitionState viewDefinitionState) {
        for (ViewLifecycleHook lifecycleHook : lifecycleHooks.get(type)) {
            lifecycleHook.callWithViewState(viewDefinitionState);
        }
    }

    public void callConstructionHooks(final ViewDefinition viewDefinition, final JSONObject jsonObject, final Locale locale) {
        for (ViewConstructionHook constructionHook : constructionHooks) {
            constructionHook.callWithJSONObject(viewDefinition, jsonObject, locale);
        }
    }
}
