package com.qcadoo.view.api.components;

import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ComponentState;

/**
 * Represents form component
 * 
 * @since 0.4.0
 */
public interface FormComponent extends ComponentState {

    /**
     * Gets id of this form entity
     * 
     * @return id of entity
     */
    Long getEntityId();

    /**
     * Returns entity filled with this forms values
     * 
     * @return entity filled with this forms values
     */
    Entity getEntity();

    /**
     * Checks if all fields of this form and entity itself are valid
     * 
     * @return false when at least one field is not valid, true otherwise
     */
    boolean isValid();

    /**
     * Enables or disables this form and all its inner components
     * 
     * @param enabled
     *            true if this form and all its inner components should be enabled, false if disabled
     */
    void setFormEnabled(boolean enabled);

}