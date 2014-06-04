/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.3
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
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
    public void addGlobalError(final String message, final boolean autoClose, final String... vars) {
        globalErrors.add(new ErrorMessage(message, autoClose, vars));
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
