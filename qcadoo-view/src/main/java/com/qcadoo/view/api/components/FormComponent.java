package com.qcadoo.view.api.components;

import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ComponentState;

public interface FormComponent extends ComponentState {

    Long getEntityId();

    Entity getEntity();

    boolean isValid();

    void setEnabledWithChildren(boolean enabled);

}