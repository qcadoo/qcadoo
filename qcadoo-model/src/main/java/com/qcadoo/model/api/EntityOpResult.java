package com.qcadoo.model.api;

import com.qcadoo.model.internal.EntityMessagesHolderImpl;

public class EntityOpResult {

    private final boolean successfull;

    private final EntityMessagesHolder messagesHolder;

    public static EntityOpResult failure(final EntityMessagesHolder messagesHolder) {
        return new EntityOpResult(false, messagesHolder);
    }

    public static EntityOpResult successfull() {
        return new EntityOpResult(true, new EntityMessagesHolderImpl());
    }

    public EntityOpResult(final boolean result, final EntityMessagesHolder messagesHolder) {
        this.successfull = result;
        this.messagesHolder = new EntityMessagesHolderImpl(messagesHolder);
    }

    public boolean isSuccessfull() {
        return successfull;
    }

    public EntityMessagesHolder getMessagesHolder() {
        return messagesHolder;
    }

}
