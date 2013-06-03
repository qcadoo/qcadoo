package com.qcadoo.view.api;

import com.qcadoo.model.api.validators.ErrorMessage;
import com.qcadoo.view.api.ComponentState.MessageType;

/**
 * ComponentMessagesHolder is simple container of component-related messages to display.
 * 
 * @author marcinkubala
 * @since 1.2.1
 */
public interface ComponentMessagesHolder {

    /**
     * Adds message to this component with type set to MessageType.FAILURE. Message will automatically close after some time.
     * 
     * @param errorMessage
     *            validation error message
     */
    void addMessage(final ErrorMessage errorMessage);

    /**
     * Adds message (translated using given key) to this component. Message will automatically close after some time.
     * 
     * @param messageTranslationKey
     *            translation key for message content
     * @param type
     *            message type
     * @param args
     *            message's arguments
     */
    void addMessage(final String messageTranslationKey, final MessageType type, String... args);

    /**
     * Adds message (translated using given key) to this component.
     * 
     * @param messageTranslationKey
     *            translation key for message content
     * @param type
     *            message type
     * @param autoClose
     *            true if this message should automatically close after some time
     * @param args
     *            message's arguments
     */
    void addMessage(final String messageTranslationKey, final MessageType type, final boolean autoClose, final String... args);

    /**
     * Adds already translated message to this component. Message will automatically close after some time.
     * 
     * @param translatedMessage
     *            translated message content
     * @param type
     *            message type
     * @param autoClose
     *            true if this message should automatically close after some time
     */
    void addTranslatedMessage(final String translatedMessage, final MessageType type);

    /**
     * Adds already translated message to this component.
     * 
     * @param translatedMessage
     *            translated message content
     * @param type
     *            message type
     * @param autoClose
     *            true if this message should automatically close after some time
     */
    void addTranslatedMessage(final String translatedMessage, final MessageType type, final boolean autoClose);
}
