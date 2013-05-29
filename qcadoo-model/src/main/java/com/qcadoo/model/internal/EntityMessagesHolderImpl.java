package com.qcadoo.model.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qcadoo.model.api.EntityMessagesHolder;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.validators.ErrorMessage;

public final class EntityMessagesHolderImpl implements EntityMessagesHolder {

    private final List<ErrorMessage> globalErrors;

    private final Map<String, ErrorMessage> fieldErrors;

    public EntityMessagesHolderImpl() {
        globalErrors = Lists.newArrayList();
        fieldErrors = Maps.newHashMap();
    }

    public EntityMessagesHolderImpl(final EntityMessagesHolder messagesHolder) {
        globalErrors = Lists.newArrayList(messagesHolder.getGlobalErrors());
        fieldErrors = Maps.newHashMap(messagesHolder.getErrors());
    }

    @Override
    public void addGlobalError(final String message, final String... vars) {
        globalErrors.add(new ErrorMessage(message, vars));
    }

    @Override
    public void addError(final FieldDefinition fieldDefinition, final String message, final String... vars) {
        fieldErrors.put(fieldDefinition.getName(), new ErrorMessage(message, vars));
    }

    @Override
    public List<ErrorMessage> getGlobalErrors() {
        return Collections.unmodifiableList(globalErrors);
    }

    @Override
    public Map<String, ErrorMessage> getErrors() {
        return Collections.unmodifiableMap(fieldErrors);
    }

    @Override
    public ErrorMessage getError(final String fieldName) {
        return fieldErrors.get(fieldName);
    }

}
