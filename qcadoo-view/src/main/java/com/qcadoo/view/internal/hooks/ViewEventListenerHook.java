package com.qcadoo.view.internal.hooks;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.context.ApplicationContext;

import com.qcadoo.model.internal.hooks.HookInitializationException;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.internal.internal.EventHandlerHolder;

public class ViewEventListenerHook extends AbstractViewHookDefinition implements EventHandlerHolder.EventHandler {

    private final String eventName;

    public ViewEventListenerHook(final String eventName, final String className, final String methodName,
            final String pluginIdentifier, final ApplicationContext applicationContext) throws HookInitializationException {
        super(className, methodName, pluginIdentifier, applicationContext);
        this.eventName = eventName;
    }

    @Override
    protected Class<?>[] getParameterTypes() {
        return new Class<?>[] { ViewDefinitionState.class, ComponentState.class, String[].class };
    }

    @Override
    public void invokeEvent(final ViewDefinitionState viewDefinitionState, final ComponentState eventPerformer,
            final String[] args) {
        performCall(viewDefinitionState, eventPerformer, args);
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ViewEventListenerHook rhs = (ViewEventListenerHook) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.eventName, rhs.eventName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(eventName).toHashCode();
    }
}
