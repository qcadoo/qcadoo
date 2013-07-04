package com.qcadoo.model.api;

import java.util.List;
import java.util.Map;

import com.qcadoo.model.api.validators.ErrorMessage;

public interface EntityMessagesHolder {

    /**
     * Set global error, not related with fields.
     * 
     * @param message
     *            message
     * @param vars
     *            message's vars
     */
    void addGlobalError(final String message, final String... vars);

    /**
     * Set global error, not related with fields.
     * 
     * @param message
     *            message
     * @param autoClose
     *            autoClose
     * @param vars
     *            message's vars
     */
    void addGlobalError(final String message, final boolean autoClose, final String... vars);

    /**
     * Set error for given field.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param message
     *            message
     * @param vars
     *            message's vars
     */
    void addError(final FieldDefinition fieldDefinition, final String message, final String... vars);

    /**
     * Return all global errors.
     * 
     * @return errors
     */
    List<ErrorMessage> getGlobalErrors();

    /**
     * Return all field's errors.
     * 
     * @return fields' errors
     */
    Map<String, ErrorMessage> getErrors();

    /**
     * Return error for given field.
     * 
     * @param fieldName
     *            field's name
     * @return field's error
     */
    ErrorMessage getError(final String fieldName);

}
