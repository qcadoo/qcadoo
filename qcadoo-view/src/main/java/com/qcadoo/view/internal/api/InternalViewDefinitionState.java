package com.qcadoo.view.internal.api;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;

public interface InternalViewDefinitionState extends ViewDefinitionState {

    /**
     * Performs event on this view. <b>For internal usage only</b>
     * 
     * @param path
     *            dotted separated path and name of component that send this event. If null than this event will be executed on
     *            all components inside this view.
     * @param event
     *            event name
     * @param args
     *            event additional arguments
     */
    void performEvent(String path, String event, String... args);

    /**
     * Registers new component into this view.
     * 
     * @param reference
     *            reference name of newly registered component
     * @param state
     *            component state to register
     */
    void registerComponent(String reference, ComponentState state);
}
