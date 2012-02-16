/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.2
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
package com.qcadoo.view.internal.components.grid;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.api.types.DataDefinitionHolder;
import com.qcadoo.model.api.types.EnumeratedType;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.api.types.JoinFieldHolder;
import com.qcadoo.model.api.types.ManyToManyType;
import com.qcadoo.model.api.validators.ErrorMessage;
import com.qcadoo.model.internal.ProxyEntity;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.internal.states.AbstractComponentState;

public final class GridComponentState extends AbstractComponentState implements GridComponent {

    enum ExportMode {
        ALL, SELECTED
    };

    public static final String JSON_SELECTED_ENTITY_ID = "selectedEntityId";

    public static final String JSON_BELONGS_TO_ENTITY_ID = "belongsToEntityId";

    public static final String JSON_FIRST_ENTITY = "firstEntity";

    public static final String JSON_MAX_ENTITIES = "maxEntities";

    public static final String JSON_TOTAL_ENTITIES = "totalEntities";

    public static final String JSON_ORDER = "order";

    public static final String JSON_ONLY_ACTIVE = "onlyActive";

    public static final String JSON_ORDER_COLUMN = "column";

    public static final String JSON_ORDER_DIRECTION = "direction";

    public static final String JSON_FILTERS = "filters";

    public static final String JSON_FILTERS_ENABLED = "filtersEnabled";

    public static final String JSON_ENTITIES = "entities";

    public static final String JSON_EDITABLE = "isEditable";

    public static final String JSON_MULTISELECT_MODE = "multiselectMode";

    public static final String JSON_SELECTED_ENTITIES = "selectedEntities";

    public static final String JSON_ENTITIES_TO_MARK_AS_NEW = "entitiesToMarkAsNew";

    private final GridEventPerformer eventPerformer = new GridEventPerformer();

    private final Map<String, GridComponentColumn> columns;

    private final FieldDefinition belongsToFieldDefinition;

    private Long selectedEntityId;

    private Long belongsToEntityId;

    private List<Entity> entities;

    private int totalEntities;

    private int firstResult;

    private int maxResults = Integer.MAX_VALUE;

    private boolean filtersEnabled = true;

    private String orderColumn;

    private String orderDirection;

    private boolean multiselectMode = false;

    private Boolean isEditable = null;

    private Set<Long> selectedEntities = new HashSet<Long>();

    private Set<Long> entitiesToMarkAsNew = new HashSet<Long>();

    private CustomRestriction customRestriction;

    private final Map<String, String> filters = new HashMap<String, String>();

    private boolean onlyActive = true;

    private final boolean activable;

    private final boolean weakRelation;

    private final DataDefinition scopeFieldDataDefinition;

    public GridComponentState(final FieldDefinition scopeField, final Map<String, GridComponentColumn> columns,
            final String orderColumn, final String orderDirection, final boolean activable, final boolean weakRelation,
            final DataDefinition dataDefinition) {
        this.belongsToFieldDefinition = scopeField;
        this.columns = columns;
        this.orderColumn = orderColumn;
        this.orderDirection = orderDirection;
        this.activable = activable;
        this.weakRelation = weakRelation;
        this.scopeFieldDataDefinition = dataDefinition;
        registerEvent("refresh", eventPerformer, "refresh");
        registerEvent("select", eventPerformer, "selectEntity");
        registerEvent("addExistingEntity", eventPerformer, "addExistingEntity");
        registerEvent("remove", eventPerformer, "removeSelectedEntity");
        registerEvent("moveUp", eventPerformer, "moveUpSelectedEntity");
        registerEvent("moveDown", eventPerformer, "moveDownSelectedEntity");
        registerEvent("copy", eventPerformer, "copySelectedEntity");
        registerEvent("activate", eventPerformer, "activateSelectedEntity");
        registerEvent("deactivate", eventPerformer, "deactivateSelectedEntity");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initializeContext(final JSONObject json) throws JSONException {
        super.initializeContext(json);

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String field = iterator.next();
            if (JSON_BELONGS_TO_ENTITY_ID.equals(field)) {
                onScopeEntityIdChange(json.getLong(field));
            } else if (JSON_COMPONENT_OPTIONS.equals(field)) {
                JSONObject jsonOptions = json.getJSONObject(JSON_COMPONENT_OPTIONS);
                passFiltersFromJson(jsonOptions);
                passSelectedEntityIdFromJson(jsonOptions);
                passSelectedEntitiesFromJson(jsonOptions);
                passEntitiesFromJson(jsonOptions);
            }
        }
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {
        if (json.has(JSON_SELECTED_ENTITY_ID) && !json.isNull(JSON_SELECTED_ENTITY_ID)) {
            selectedEntityId = json.getLong(JSON_SELECTED_ENTITY_ID);
        }
        if (json.has(JSON_MULTISELECT_MODE) && !json.isNull(JSON_MULTISELECT_MODE)) {
            multiselectMode = json.getBoolean(JSON_MULTISELECT_MODE);
        }
        if (json.has(JSON_SELECTED_ENTITIES) && !json.isNull(JSON_SELECTED_ENTITIES)) {
            JSONObject selectedEntitiesObj = json.getJSONObject(JSON_SELECTED_ENTITIES);
            JSONArray selectedEntitiesIds = selectedEntitiesObj.names();
            for (int i = 0; selectedEntitiesIds != null && i < selectedEntitiesIds.length(); i++) {
                String key = selectedEntitiesIds.getString(i);
                boolean isSelected = false;
                if (selectedEntitiesObj.has(key) && !selectedEntitiesObj.isNull(key)) {
                    isSelected = selectedEntitiesObj.getBoolean(key);
                }
                if (isSelected) {
                    selectedEntities.add(Long.parseLong(key));
                }
            }
        }
        if (json.has(JSON_BELONGS_TO_ENTITY_ID) && !json.isNull(JSON_BELONGS_TO_ENTITY_ID)) {
            belongsToEntityId = json.getLong(JSON_BELONGS_TO_ENTITY_ID);
        }
        if (json.has(JSON_FIRST_ENTITY) && !json.isNull(JSON_FIRST_ENTITY)) {
            firstResult = json.getInt(JSON_FIRST_ENTITY);
        }
        if (json.has(JSON_MAX_ENTITIES) && !json.isNull(JSON_MAX_ENTITIES)) {
            maxResults = json.getInt(JSON_MAX_ENTITIES);
        }
        if (json.has(JSON_ONLY_ACTIVE) && !json.isNull(JSON_ONLY_ACTIVE) && activable) {
            onlyActive = json.getBoolean(JSON_ONLY_ACTIVE);
        }
        if (json.has(JSON_ORDER) && !json.isNull(JSON_ORDER)) {
            JSONObject orderJson = json.getJSONObject(JSON_ORDER);
            if (orderJson.has(JSON_ORDER_COLUMN) && orderJson.has(JSON_ORDER_DIRECTION)) {
                orderColumn = orderJson.getString(JSON_ORDER_COLUMN);
                orderDirection = orderJson.getString(JSON_ORDER_DIRECTION);
            }
        }
        if (belongsToFieldDefinition != null && belongsToEntityId == null) {
            setEnabled(false);
        }

        passFiltersFromJson(json);

        requestRender();
        requestUpdateState();
    }

    @SuppressWarnings("unchecked")
    private void passFiltersFromJson(final JSONObject json) throws JSONException {
        if (json.has(JSON_FILTERS_ENABLED) && !json.isNull(JSON_FILTERS_ENABLED)) {
            filtersEnabled = json.getBoolean(JSON_FILTERS_ENABLED);
        }
        if (json.has(JSON_FILTERS) && !json.isNull(JSON_FILTERS)) {
            filtersEnabled = true;
            JSONObject filtersJson = json.getJSONObject(JSON_FILTERS);
            Iterator<String> filtersKeys = filtersJson.keys();
            while (filtersKeys.hasNext()) {
                String column = filtersKeys.next();
                filters.put(column, filtersJson.getString(column).trim());
            }
        }
    }

    private void passSelectedEntitiesFromJson(final JSONObject json) throws JSONException {
        if (json.has(JSON_SELECTED_ENTITIES) && !json.isNull(JSON_SELECTED_ENTITIES)) {
            selectedEntities = Sets.newHashSet();
            JSONArray entitiesToSelect = json.getJSONArray(JSON_SELECTED_ENTITIES);
            for (int i = 0; i < entitiesToSelect.length(); i++) {
                selectedEntities.add(Long.valueOf(entitiesToSelect.get(i).toString()));
            }
        }
    }

    private void passSelectedEntityIdFromJson(final JSONObject json) throws JSONException {
        if (json.has(JSON_SELECTED_ENTITY_ID) && !json.isNull(JSON_SELECTED_ENTITY_ID)) {
            selectedEntityId = json.getLong(JSON_SELECTED_ENTITY_ID);
        }
    }

    private void passEntitiesFromJson(final JSONObject json) throws JSONException {
        if (gridIsEmpty() && json.has(JSON_ENTITIES) && !json.isNull(JSON_ENTITIES)) {
            entities = Lists.newArrayList();
            JSONArray givenEntities = json.getJSONArray(JSON_ENTITIES);
            Long entityId = null;
            for (int i = 0; i < givenEntities.length(); i++) {
                entityId = Long.valueOf(givenEntities.get(i).toString());
                entities.add(getDataDefinition().get(entityId));
            }
        }
    }

    private boolean gridIsEmpty() {
        return belongsToEntityId == null;
    }

    @Override
    public void onFieldEntityIdChange(final Long entityId) {
        setSelectedEntityId(entityId);
    }

    @Override
    public void onScopeEntityIdChange(final Long scopeEntityId) {
        if (belongsToFieldDefinition == null) {
            throw new IllegalStateException("Grid doesn't have scopeField, it cannot set scopeEntityId");
        }

        if (belongsToEntityId != null && !belongsToEntityId.equals(scopeEntityId)) {
            setSelectedEntityId(null);
            selectedEntities = new HashSet<Long>();
            multiselectMode = false;
        }
        belongsToEntityId = scopeEntityId;
        setEnabled(scopeEntityId != null);
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        if (entities == null) {
            eventPerformer.reload();
        }

        if (entities == null) {
            throw new IllegalStateException("Cannot load entities for grid component");
        }

        JSONObject json = new JSONObject();
        json.put(JSON_SELECTED_ENTITY_ID, selectedEntityId);
        json.put(JSON_BELONGS_TO_ENTITY_ID, belongsToEntityId);
        json.put(JSON_FIRST_ENTITY, firstResult);
        json.put(JSON_MAX_ENTITIES, maxResults);
        json.put(JSON_FILTERS_ENABLED, filtersEnabled);
        json.put(JSON_TOTAL_ENTITIES, totalEntities);
        json.put(JSON_ONLY_ACTIVE, onlyActive);

        json.put(JSON_MULTISELECT_MODE, multiselectMode);
        JSONObject selectedEntitiesJson = new JSONObject();
        for (Long entityId : selectedEntities) {
            selectedEntitiesJson.put(entityId.toString(), true);
        }
        json.put(JSON_SELECTED_ENTITIES, selectedEntitiesJson);

        if (isEditable != null) {
            json.put(JSON_EDITABLE, isEditable);
        }

        if (!entitiesToMarkAsNew.isEmpty()) {
            JSONObject entitiesToMarkAsNewJson = new JSONObject();
            for (Long entityId : entitiesToMarkAsNew) {
                entitiesToMarkAsNewJson.put(entityId.toString(), true);
            }
            json.put(JSON_ENTITIES_TO_MARK_AS_NEW, entitiesToMarkAsNewJson);
        }

        if (orderColumn != null) {
            JSONObject jsonOrder = new JSONObject();
            jsonOrder.put(JSON_ORDER_COLUMN, orderColumn);
            jsonOrder.put(JSON_ORDER_DIRECTION, orderDirection);
            json.put(JSON_ORDER, jsonOrder);
        }

        JSONObject jsonFilters = new JSONObject();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            jsonFilters.put(entry.getKey(), entry.getValue());
        }

        json.put(JSON_FILTERS, jsonFilters);

        JSONArray jsonEntities = new JSONArray();
        for (Entity entity : entities) {
            jsonEntities.put(convertEntityToJson(entity));
        }

        json.put(JSON_ENTITIES, jsonEntities);

        return json;
    }

    private JSONObject convertEntityToJson(final Entity entity) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", entity.getId());
        if (activable) {
            json.put("active", entity.isActive());
        } else {
            json.put("active", true);
        }
        JSONObject fields = new JSONObject();
        for (GridComponentColumn column : columns.values()) {
            fields.put(column.getName(), column.getValue(entity, getLocale()));
        }
        json.put("fields", fields);

        return json;
    }

    @Override
    public Set<Long> getSelectedEntitiesIds() {
        return selectedEntities;
    }

    @Override
    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    public void setSelectedEntitiesIds(final Set<Long> selectedEntities) {
        this.selectedEntities = selectedEntities;
        if (selectedEntities == null || selectedEntities.size() < 2) {
            multiselectMode = false;
        } else {
            multiselectMode = true;
        }
    }

    @Override
    public void setEntities(final List<Entity> entities) {
        this.entities = entities;
        totalEntities = entities.size();
    }

    @Override
    public List<Entity> getEntities() {
        if (entities == null) {
            eventPerformer.reload();
        }
        return entities;
    }

    private void setSelectedEntityId(final Long selectedEntityId) {
        this.selectedEntityId = selectedEntityId;
        notifyEntityIdChangeListeners(selectedEntityId);
    }

    private Long getSelectedEntityId() {
        return selectedEntityId;
    }

    @Override
    public Object getFieldValue() {
        return getSelectedEntityId();
    }

    @Override
    public void setFieldValue(final Object value) {
        setSelectedEntityId((Long) value);
    }

    @Override
    public void setCustomRestriction(final CustomRestriction customRestriction) {
        this.customRestriction = customRestriction;
    }

    protected class GridEventPerformer {

        public void refresh(final String[] args) {
            // nothing interesting here
        }

        public void selectEntity(final String[] args) {
            notifyEntityIdChangeListeners(getSelectedEntityId());
        }

        public void addExistingEntity(final String[] selectedEntities) throws JSONException {
            if (!weakRelation || selectedEntities.length == 0) {
                return;
            }

            JSONArray selectedEntitiesArray = null;
            if (selectedEntities[0].contains("[")) {
                selectedEntitiesArray = new JSONArray(selectedEntities[0]);
            } else {
                selectedEntitiesArray = new JSONArray(selectedEntities);
            }

            List<Long> selectedEntitiesId = Lists.newArrayList();
            for (int i = 0; i < selectedEntitiesArray.length(); i++) {
                selectedEntitiesId.add(Long.parseLong(selectedEntitiesArray.getString(i)));
            }

            List<Entity> existingEntities = getEntities();
            List<Entity> newlyAddedEntities = getDataDefinition().find().add(SearchRestrictions.in("id", selectedEntitiesId))
                    .list().getEntities();

            entitiesToMarkAsNew = Sets.newHashSet(selectedEntitiesId);
            for (Entity existingEntity : existingEntities) {
                entitiesToMarkAsNew.remove(existingEntity.getId());
            }

            existingEntities.addAll(newlyAddedEntities);

            FieldType belongsToFieldType = belongsToFieldDefinition.getType();
            if (belongsToFieldType instanceof JoinFieldHolder) {
                Entity gridOwnerEntity = scopeFieldDataDefinition.get(belongsToEntityId);
                gridOwnerEntity.setField(((JoinFieldHolder) belongsToFieldType).getJoinFieldName(), existingEntities);
                gridOwnerEntity.getDataDefinition().save(gridOwnerEntity);
                copyFieldValidationMessages(gridOwnerEntity);
            } else if (belongsToFieldType instanceof BelongsToType) {
                for (Entity entity : newlyAddedEntities) {
                    entity.setField(belongsToFieldDefinition.getName(), belongsToEntityId);
                    entity.getDataDefinition().save(entity);
                    copyFieldValidationMessages(entity);
                }
            } else {
                throw new IllegalArgumentException("Unsupported relation type - " + belongsToFieldDefinition.getType().toString());
            }

            reload();
        }

        public void removeSelectedEntity(final String[] args) {
            if (weakRelation) {
                Entity entity = null;
                boolean isManyToManyRelationType = belongsToFieldDefinition.getType() instanceof JoinFieldHolder;
                Long[] selectedEntitiesIds = selectedEntities.toArray(new Long[selectedEntities.size()]);

                if (isManyToManyRelationType) {
                    String gridFieldName = ((JoinFieldHolder) belongsToFieldDefinition.getType()).getJoinFieldName();
                    Entity gridOwnerEntity = scopeFieldDataDefinition.get(belongsToEntityId);

                    List<Entity> relatedEntities = gridOwnerEntity.getManyToManyField(gridFieldName);
                    for (Long selectedId : selectedEntitiesIds) {
                        relatedEntities.remove(new ProxyEntity(getDataDefinition(), selectedId));
                    }
                    gridOwnerEntity.setField(gridFieldName, relatedEntities);
                    scopeFieldDataDefinition.save(gridOwnerEntity);
                    copyFieldValidationMessages(gridOwnerEntity);
                } else {
                    for (Long selectedId : selectedEntitiesIds) {
                        entity = getDataDefinition().get(selectedId);
                        entity.setField(belongsToFieldDefinition.getName(), null);
                        getDataDefinition().save(entity);
                        copyFieldValidationMessages(entity);
                    }
                }
            } else {
                getDataDefinition().delete(selectedEntities.toArray(new Long[selectedEntities.size()]));
            }
            if (selectedEntities.size() == 1) {
                addTranslatedMessage(translateMessage("deleteMessage"), MessageType.SUCCESS);
            } else {
                addTranslatedMessage(translateMessage("deleteMessages", String.valueOf(selectedEntities.size())),
                        MessageType.SUCCESS);
            }
            setSelectedEntityId(null);
            multiselectMode = false;
            selectedEntities = new HashSet<Long>();
        }

        public void moveUpSelectedEntity(final String[] args) {
            getDataDefinition().move(selectedEntityId, -1);
            addTranslatedMessage(translateMessage("moveMessage"), MessageType.SUCCESS);
        }

        public void deactivateSelectedEntity(final String[] args) {
            List<Entity> deactivatedEntities = getDataDefinition().deactivate(
                    selectedEntities.toArray(new Long[selectedEntities.size()]));

            entitiesToMarkAsNew = new HashSet<Long>();
            for (Entity entity : deactivatedEntities) {
                entitiesToMarkAsNew.add(entity.getId());
            }

            if (selectedEntities.size() == 1) {
                addTranslatedMessage(translateMessage("deactivateMessage"), MessageType.SUCCESS);
            } else {
                addTranslatedMessage(translateMessage("deactivateMessages", String.valueOf(selectedEntities.size())),
                        MessageType.SUCCESS);
            }
        }

        public void activateSelectedEntity(final String[] args) {
            List<Entity> activatedEntities = getDataDefinition().activate(
                    selectedEntities.toArray(new Long[selectedEntities.size()]));

            entitiesToMarkAsNew = new HashSet<Long>();
            for (Entity entity : activatedEntities) {
                entitiesToMarkAsNew.add(entity.getId());
            }

            if (selectedEntities.size() == 1) {
                addTranslatedMessage(translateMessage("activateMessage"), MessageType.SUCCESS);
            } else {
                addTranslatedMessage(translateMessage("activateMessages", String.valueOf(selectedEntities.size())),
                        MessageType.SUCCESS);
            }
        }

        public void copySelectedEntity(final String[] args) {
            List<Entity> copiedEntities = getDataDefinition().copy(selectedEntities.toArray(new Long[selectedEntities.size()]));
            entitiesToMarkAsNew = new HashSet<Long>();
            for (Entity copiedEntity : copiedEntities) {
                entitiesToMarkAsNew.add(copiedEntity.getId());
            }

            if (selectedEntities.size() == 1) {
                addTranslatedMessage(translateMessage("copyMessage"), MessageType.SUCCESS);
            } else {
                addTranslatedMessage(translateMessage("copyMessages", String.valueOf(selectedEntities.size())),
                        MessageType.SUCCESS);
            }
        }

        public void moveDownSelectedEntity(final String[] args) {
            getDataDefinition().move(selectedEntityId, 1);
            addTranslatedMessage(translateMessage("moveMessage"), MessageType.SUCCESS);
        }

        private void reload() {
            if (belongsToFieldDefinition == null || belongsToEntityId != null) {
                SearchCriteriaBuilder criteria = getDataDefinition().find();
                if (belongsToFieldDefinition != null) {
                    if (belongsToFieldDefinition.getType() instanceof ManyToManyType) {
                        String belongsToFieldName = belongsToFieldDefinition.getName();
                        criteria.createAlias(belongsToFieldName, belongsToFieldName).add(
                                SearchRestrictions.eq(belongsToFieldName + ".id", belongsToEntityId));
                    } else {
                        criteria.add(SearchRestrictions.belongsTo(belongsToFieldDefinition.getName(),
                                ((DataDefinitionHolder) belongsToFieldDefinition.getType()).getDataDefinition(),
                                belongsToEntityId));
                    }
                }

                try {
                    if (filtersEnabled) {
                        GridComponentFilterUtils.addFilters(filters, columns, getDataDefinition(), criteria);
                    }

                    if (customRestriction != null) {
                        customRestriction.addRestriction(criteria);
                    }

                    if (activable && onlyActive) {
                        criteria.add(SearchRestrictions.eq("active", true));
                    }

                    addOrder(criteria);
                    addPaging(criteria);

                    SearchResult result = criteria.list();

                    if (repeatWithFixedFirstResult(result)) {
                        addPaging(criteria);
                        result = criteria.list();
                    }

                    entities = result.getEntities();
                    totalEntities = result.getTotalNumberOfEntities();
                } catch (ParseException e) {
                    entities = Collections.emptyList();
                    totalEntities = 0;
                }
            } else {
                entities = Collections.emptyList();
                totalEntities = 0;
            }
        }

        private void addPaging(final SearchCriteriaBuilder criteria) {
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(maxResults);
        }

        private void addOrder(final SearchCriteriaBuilder criteria) {
            if (orderColumn != null) {
                String field = GridComponentFilterUtils.getFieldNameByColumnName(columns, orderColumn);

                field = GridComponentFilterUtils.addAliases(criteria, field);

                if (field != null) {
                    if ("asc".equals(orderDirection)) {
                        criteria.addOrder(SearchOrders.asc(field));
                    } else {
                        criteria.addOrder(SearchOrders.desc(field));
                    }
                }
            }
        }

        private boolean repeatWithFixedFirstResult(final SearchResult result) {
            if (result.getEntities().isEmpty() && result.getTotalNumberOfEntities() > 0) {
                while (firstResult >= result.getTotalNumberOfEntities()) {
                    firstResult -= maxResults;
                }
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Map<String, String> getColumnNames() {
        Map<String, String> names = new LinkedHashMap<String, String>();

        for (GridComponentColumn column : columns.values()) {
            if (column.isHidden()) {
                continue;
            }
            if (column.getFields().size() == 1) {
                String fieldCode = getDataDefinition().getPluginIdentifier() + "." + getDataDefinition().getName() + "."
                        + column.getFields().get(0).getName();
                names.put(
                        column.getName(),
                        getTranslationService().translate(getTranslationPath() + ".column." + column.getName(),
                                fieldCode + ".label", getLocale()));
            } else {
                names.put(column.getName(),
                        getTranslationService().translate(getTranslationPath() + ".column." + column.getName(), getLocale()));
            }
        }

        return names;
    }

    @Override
    public List<Map<String, String>> getColumnValuesOfAllRecords() {
        return getColumnValues(ExportMode.ALL);
    }

    @Override
    public List<Map<String, String>> getColumnValuesOfSelectedRecords() {
        return getColumnValues(ExportMode.SELECTED);
    }

    private List<Map<String, String>> getColumnValues(final ExportMode mode) {
        if (entities == null) {
            eventPerformer.reload();
        }

        if (entities == null) {
            throw new IllegalStateException("Cannot load entities for grid component");
        }

        List<Map<String, String>> values = new ArrayList<Map<String, String>>();

        for (Entity entity : entities) {
            if (mode == ExportMode.ALL || (mode == ExportMode.SELECTED && getSelectedEntitiesIds().contains(entity.getId()))) {
                values.add(convertEntityToMap(entity));
            }
        }

        return values;
    }

    private Map<String, String> convertEntityToMap(final Entity entity) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        for (GridComponentColumn column : columns.values()) {
            if (column.isHidden()) {
                continue;
            }

            if (column.getFields().get(0).getType() instanceof EnumeratedType) {
                String fieldValue = column.getValue(entity, getLocale());

                StringBuffer localeString = new StringBuffer();
                localeString.append(getDataDefinition().getPluginIdentifier());
                localeString.append('.');
                localeString.append(getDataDefinition().getName());
                localeString.append('.');
                localeString.append(column.getName());
                localeString.append(".value.");
                localeString.append(fieldValue);

                values.put(column.getName(), getTranslationService().translate(localeString.toString(), getLocale()));
            } else {
                values.put(column.getName(), column.getValue(entity, getLocale()));
            }
        }
        return values;
    }

    private void copyFieldValidationMessages(final Entity messagesSource) {
        for (ErrorMessage message : messagesSource.getErrors().values()) {
            addMessage(message);
        }
    }

}