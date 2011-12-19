/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.0
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
package com.qcadoo.view.internal.components.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.expression.ExpressionUtils;
import com.qcadoo.model.api.validators.ErrorMessage;
import com.qcadoo.model.internal.DetachedEntityTreeImpl;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.internal.FieldEntityIdChangeListener;
import com.qcadoo.view.internal.ScopeEntityIdChangeListener;
import com.qcadoo.view.internal.components.FieldComponentState;
import com.qcadoo.view.internal.components.lookup.LookupComponentState;
import com.qcadoo.view.internal.components.tree.TreeComponentState;
import com.qcadoo.view.internal.states.AbstractContainerState;

public class FormComponentState extends AbstractContainerState implements FormComponent {

    public static final String JSON_ENTITY_ID = "entityId";

    public static final String JSON_IS_ACTIVE = "isActive";

    public static final String JSON_VALID = "valid";

    public static final String JSON_HEADER = "header";

    public static final String JSON_HEADER_ENTITY_IDENTIFIER = "headerEntityIdentifier";

    public static final String INITIALIZE_EVENT_NAME = "initialize";

    private Long entityId;

    private Long contextEntityId;

    private boolean active;

    private boolean valid = true;

    private final Map<String, Object> context = new HashMap<String, Object>();

    private final FormEventPerformer eventPerformer = new FormEventPerformer();

    private final String expressionEdit;

    private Map<String, FieldComponentState> fieldComponents;

    private final String expressionNew;

    public FormComponentState(final String expressionNew, final String expressionEdit) {
        this.expressionNew = expressionNew;
        this.expressionEdit = expressionEdit;
        registerEvent("clear", eventPerformer, "clear");
        registerEvent("save", eventPerformer, "save");
        registerEvent("saveAndClear", eventPerformer, "saveAndClear");
        registerEvent(INITIALIZE_EVENT_NAME, eventPerformer, INITIALIZE_EVENT_NAME);
        registerEvent("initializeAfterBack", eventPerformer, INITIALIZE_EVENT_NAME);
        registerEvent("reset", eventPerformer, INITIALIZE_EVENT_NAME);
        registerEvent("delete", eventPerformer, "delete");
        registerEvent("copy", eventPerformer, "copy");
        registerEvent("activate", eventPerformer, "activate");
        registerEvent("deactivate", eventPerformer, "deactivate");
    }

    @Override
    public void onFieldEntityIdChange(final Long entityId) {
        setFieldValue(entityId);
        eventPerformer.initialize(new String[0]);
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {
        if (!json.has(JSON_ENTITY_ID)) {
            return;
        }
        if (json.isNull(JSON_ENTITY_ID)) {
            entityId = null;
        } else {
            entityId = json.getLong(JSON_ENTITY_ID);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeContext(final JSONObject json) throws JSONException {
        super.initializeContext(json);

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String field = iterator.next();
            if ("id".equals(field)) {
                if (entityId == null) {
                    contextEntityId = json.getLong(field);
                }
            } else if (!json.isNull(field)) {
                context.put(field, json.get(field));
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.qcadoo.view.components.form.EntityComponentState#getEntityId()
     */
    @Override
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
        requestRender();
        requestUpdateState();
        notifyEntityIdChangeListeners(entityId);
    }

    @Override
    public void setFieldValue(final Object value) {
        setEntityId((Long) value);
    }

    /*
     * (non-Javadoc)
     * @see com.qcadoo.view.components.form.EntityComponentState#getEntity()
     */
    @Override
    public Entity getEntity() {
        Entity entity = getDataDefinition().create(entityId);
        copyFieldsToEntity(entity);
        copyContextToEntity(entity);
        return entity;
    }

    public void setEntity(final Entity entity) {
        if (entity.isValid()) {
            active = entity.isActive();
        } else {
            valid = false;
            requestRender();
            copyMessages(entity.getGlobalErrors());
        }

        copyEntityToFields(entity, entity.isValid());
        setEntityId(entity.getId());
        setFieldsRequiredAndDisables();

    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean isHasError() { // form never has error - its field can have
        for (ComponentState child : getChildren().values()) {
            if (child.isHasError()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getFieldValue() {
        return getEntityId();
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_VALID, isValid());
        if (entityId == null) {
            json.put(JSON_ENTITY_ID, JSONObject.NULL);
            json.put(JSON_IS_ACTIVE, active);
            json.put(JSON_HEADER, getTranslationService().translate(getTranslationPath() + ".headerNew", getLocale()));
            json.put(JSON_HEADER_ENTITY_IDENTIFIER, getHeaderNew());
        } else {
            json.put(JSON_ENTITY_ID, entityId);
            json.put(JSON_IS_ACTIVE, active);
            json.put(JSON_HEADER, getTranslationService().translate(getTranslationPath() + ".headerEdit", getLocale()));
            json.put(JSON_HEADER_ENTITY_IDENTIFIER, getHeaderEdit());
        }
        return json;
    }

    private String getHeaderEdit() {
        Entity entity = getDataDefinition().get(entityId);
        return ExpressionUtils.getValue(entity, expressionEdit, getLocale());
    }

    private Object getHeaderNew() {
        if (expressionNew == null) {
            return JSONObject.NULL;
        } else {
            return ExpressionUtils.getValue(getEntity(), expressionNew, getLocale());
        }
    }

    private String translateMessage(final String key) {
        return getTranslationService().translate(getTranslationPath() + "." + key, "qcadooView.message." + key, getLocale());
    }

    private Map<String, FieldComponentState> getFieldComponents() {
        if (fieldComponents != null) {
            return fieldComponents;
        }

        fieldComponents = new HashMap<String, FieldComponentState>();

        for (Map.Entry<String, FieldEntityIdChangeListener> field : getFieldEntityIdChangeListeners().entrySet()) {
            if (isValidFormField(field.getKey(), field.getValue())) {
                fieldComponents.put(field.getKey(), (FieldComponentState) field.getValue());
            }
        }

        for (Map.Entry<String, ScopeEntityIdChangeListener> field : getScopeEntityIdChangeListeners().entrySet()) {
            if (!(field.getValue() instanceof FieldComponent)) {
                continue;
            }
            fieldComponents.put(field.getKey(), (FieldComponentState) field.getValue());
        }

        return fieldComponents;
    }

    private boolean isValidFormField(final String fieldName, final FieldEntityIdChangeListener component) {
        if (!(component instanceof FieldComponent)) {
            return false;
        }

        FieldDefinition field = getDataDefinition().getField(fieldName);

        if (field == null) {
            return false;
        }

        return true;
    }

    private void copyFieldsToEntity(final Entity entity) {
        FieldComponentState fieldComponentState = null;
        for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
            fieldComponentState = field.getValue();
            if (fieldComponentState.isPersistent()) {
                entity.setField(field.getKey(), convertFieldFromString(fieldComponentState.getFieldValue(), field.getKey()));
            }

            if (fieldComponentState.isHasError()) {
                entity.setNotValid();
            }
        }
    }

    private void copyContextToEntity(final Entity entity) {
        for (String field : getDataDefinition().getFields().keySet()) {
            if (context.containsKey(field)) {
                entity.setField(field, convertFieldFromString(context.get(field), field));
            }
        }
    }

    private void setFieldsRequiredAndDisables() {
        for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
            FieldDefinition fieldDefinition = getDataDefinition().getField(field.getKey());

            if (fieldDefinition.isRequired()) {
                field.getValue().setRequired(true);
            }

            if (fieldDefinition.isReadOnly()) {
                field.getValue().setEnabled(false);
            }
        }
    }

    private Object convertFieldFromString(final Object value, final String field) {
        if (value instanceof String) {
            return getDataDefinition().getField(field).getType().fromString((String) value, getLocale());
        } else {
            return value;
        }
    }

    private void copyMessages(final List<ErrorMessage> messages) {
        for (ErrorMessage message : messages) {
            copyMessage(FormComponentState.this, message);
        }
    }

    private void copyEntityToFields(final Entity entity, final boolean requestUpdateState) {
        for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
            ErrorMessage message = entity.getError(field.getKey());
            if (message != null) {
                copyMessage(field.getValue(), message);
            }
            if (fieldIsGridCorrespondingLookup(field.getValue(), field.getKey(), entity)) {
                continue;
            }
            if (fieldIsDetachedEntityTree(field.getValue(), field.getKey(), entity)) {
                EntityTree tree = entity.getTreeField(field.getKey());
                if (tree != null) {
                    ((TreeComponentState) field.getValue()).setRootNode(tree.getRoot());
                }
            }
            field.getValue().setFieldValue(convertFieldToString(entity.getField(field.getKey()), field.getKey()));
            if (requestUpdateState) {
                field.getValue().requestComponentUpdateState();
            }
        }
    }

    private boolean fieldIsGridCorrespondingLookup(final FieldComponentState field, final String databaseFieldName,
            final Entity entity) {
        return (field instanceof LookupComponentState) && (entity.getField(databaseFieldName) instanceof Collection);
    }

    private boolean fieldIsDetachedEntityTree(final Object fieldValue, final String databaseFieldName, final Entity entity) {
        return fieldValue instanceof TreeComponentState && entity.getField(databaseFieldName) instanceof DetachedEntityTreeImpl;
    }

    private Object convertFieldToString(final Object value, final String field) {
        if (value instanceof String) {
            return value;
        }

        if (value == null) {
            return "";
        }

        if (value instanceof Collection) {
            return value;
        }

        return getDataDefinition().getField(field).getType().toString(value, getLocale());
    }

    private void copyMessage(final ComponentState componentState, final ErrorMessage message) {
        if (message != null) {
            String translation = getTranslationService().translate(message.getMessage(), getLocale(), message.getVars());
            componentState.addMessage(translation, MessageType.FAILURE);
        }
    }

    @Override
    public void setFormEnabled(final boolean enabled) {
        for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
            FieldDefinition fieldDefinition = getDataDefinition().getField(field.getKey());

            if (!(fieldDefinition.isReadOnly())) {
                field.getValue().setEnabled(enabled);
                field.getValue().requestComponentUpdateState();
            }
        }
        setEnabled(enabled);
    }

    protected final class FormEventPerformer {

        public void saveAndClear(final String[] args) {
            save(args);
            if (isValid()) {
                clear(args);
            }
        }

        public void save(final String[] args) {
            Entity databaseEntity = getFormEntity();

            if (databaseEntity == null && entityId != null) {
                throw new IllegalStateException("Entity cannot be found");
            }

            Entity entity = getEntity();

            if (entity.isValid()) {
                entity = getDataDefinition().save(entity);

                setEntity(entity);
            }

            if (entity.isValid()) {
                setFieldValue(entity.getId());
                addMessage(translateMessage("saveMessage"), MessageType.SUCCESS);
            } else {
                if (entity.getGlobalErrors().size() == 0) {
                    addMessage(translateMessage("saveFailedMessage"), MessageType.FAILURE);
                }
                valid = false;
            }

            setFieldsRequiredAndDisables();
        }

        public void copy(final String[] args) {
            if (entityId == null) {
                addMessage(translateMessage("copyFailedMessage"), MessageType.FAILURE);
                return;
            }

            List<Entity> copiedEntities = getDataDefinition().copy(entityId);

            if (copiedEntities.size() > 0 && copiedEntities.get(0).getId() != null) {
                clear(args);
                setEntityId(copiedEntities.get(0).getId());
                initialize(args);
                addMessage(translateMessage("copyMessage"), MessageType.SUCCESS);
            } else {
                addMessage(translateMessage("copyFailedMessage"), MessageType.FAILURE);
            }
        }

        public void activate(final String[] args) {
            if (entityId == null) {
                addMessage(translateMessage("activateFailedMessage"), MessageType.FAILURE);
                return;
            }

            List<Entity> activatedEntities = getDataDefinition().activate(entityId);

            if (activatedEntities.size() > 0) {
                active = true;
                addMessage(translateMessage("activateMessage"), MessageType.SUCCESS);
                setEntity(activatedEntities.get(0));
            }
        }

        public void deactivate(final String[] args) {
            if (entityId == null) {
                addMessage(translateMessage("deactivateFailedMessage"), MessageType.FAILURE);
                return;
            }

            List<Entity> deactivatedEntities = getDataDefinition().deactivate(entityId);

            if (deactivatedEntities.size() > 0) {
                active = false;
                addMessage(translateMessage("deactivateMessage"), MessageType.SUCCESS);
                setEntity(deactivatedEntities.get(0));
            }
        }

        public void delete(final String[] args) {
            Entity entity = getFormEntity();
            if (entity == null) {
                throw new IllegalStateException("Entity cannot be found");
            } else if (entityId != null) {
                getDataDefinition().delete(entityId);
                addMessage(translateMessage("deleteMessage"), MessageType.SUCCESS);
                clear(args);
            }
        }

        public void initialize(final String[] args) {
            if (contextEntityId != null) {
                entityId = contextEntityId;
            }

            Entity entity = getFormEntity();
            if (entity != null) {
                active = entity.isActive();
                copyEntityToFields(entity, true);
                setFieldValue(entity.getId());
                setFieldsRequiredAndDisables();
                return;
            }

            if (entityId != null) {
                setFormEnabled(false);
                active = false;
                valid = false;
                addMessage(translateMessage("entityNotFound"), MessageType.FAILURE);
                return;
            }

            clear(args);
        }

        public void clear(final String[] args) {
            active = false;
            clearFields();
            setFieldValue(null);
            copyDefaultValuesToFields();
            setFieldsRequiredAndDisables();
        }

        private Entity getFormEntity() {
            if (entityId == null) {
                return null;
            }
            return getDataDefinition().get(entityId);
        }

        private void copyDefaultValuesToFields() {
            for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
                FieldDefinition fieldDefinition = getDataDefinition().getField(field.getKey());
                if (fieldDefinition.getDefaultValue() != null) {
                    field.getValue().setFieldValue(convertFieldToString(fieldDefinition.getDefaultValue(), field.getKey()));
                    field.getValue().requestComponentUpdateState();
                }
            }
        }

        private void clearFields() {
            for (Map.Entry<String, FieldComponentState> field : getFieldComponents().entrySet()) {
                field.getValue().setFieldValue(null);
                field.getValue().requestComponentUpdateState();
            }
        }

    }

}
