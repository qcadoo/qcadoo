package com.qcadoo.view.api.components;

import com.qcadoo.view.api.ComponentState;

/**
 * Represents universal field component.
 * 
 * @since 0.4.0
 */
public interface FieldComponent extends ComponentState {

    /**
     * Checks if field defined by this component is required
     * 
     * @return true if field defined by this component is required
     */
    boolean isRequired();

    /**
     * Sets if field is required
     * 
     * @param required
     *            true if field should be required
     */
    void setRequired(boolean required);

    /**
     * Informs that this component should be updated
     */
    void requestComponentUpdateState();

}