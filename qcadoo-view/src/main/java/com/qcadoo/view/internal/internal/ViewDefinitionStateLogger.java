package com.qcadoo.view.internal.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcadoo.view.api.ViewDefinitionState;

class ViewDefinitionStateLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewDefinitionState.class);

    private static final String MESSAGE_FORMAT = "View '%s': %s";

    private final String viewName;

    static ViewDefinitionStateLogger forView(final ViewDefinitionState view) {
        return new ViewDefinitionStateLogger(view.getName());
    }

    private ViewDefinitionStateLogger(final String viewName) {
        this.viewName = viewName;
    }

    public void logWarn(final String message) {
        logWarn(message, null);
    }

    public void logWarn(final String message, final Throwable cause) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(formatMessage(message), cause);
        }
    }

    private String formatMessage(final String message) {
        return String.format(MESSAGE_FORMAT, viewName, message);
    }

}
