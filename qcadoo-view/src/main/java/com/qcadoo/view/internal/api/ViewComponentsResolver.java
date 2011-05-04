package com.qcadoo.view.internal.api;

import java.util.Set;

import com.qcadoo.view.internal.ComponentDefinition;

public interface ViewComponentsResolver {

    boolean hasComponent(String name);

    Set<String> getAvailableComponents();

    Class<? extends ComponentPattern> getComponentClass(String name);

    ComponentPattern getComponentInstance(String name, ComponentDefinition componentDefinition);

    void register(String name, Class<? extends ComponentPattern> clazz);

}