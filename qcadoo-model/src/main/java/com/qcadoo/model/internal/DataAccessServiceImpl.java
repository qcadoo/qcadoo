/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.qcadoo.model.api.CopyException;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;
import com.qcadoo.model.api.EntityMessagesHolder;
import com.qcadoo.model.api.EntityOpResult;
import com.qcadoo.model.api.ExpressionService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.aop.Auditable;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.api.types.Cascadeable;
import com.qcadoo.model.api.types.CollectionFieldType;
import com.qcadoo.model.api.types.DataDefinitionHolder;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.api.types.HasManyType;
import com.qcadoo.model.api.types.JoinFieldHolder;
import com.qcadoo.model.api.types.ManyToManyType;
import com.qcadoo.model.api.types.TreeType;
import com.qcadoo.model.api.utils.EntityUtils;
import com.qcadoo.model.api.validators.ErrorMessage;
import com.qcadoo.model.internal.api.DataAccessService;
import com.qcadoo.model.internal.api.EntityService;
import com.qcadoo.model.internal.api.HibernateService;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.InternalFieldDefinition;
import com.qcadoo.model.internal.api.PriorityService;
import com.qcadoo.model.internal.api.ValidationService;
import com.qcadoo.model.internal.search.SearchCriteria;
import com.qcadoo.model.internal.search.SearchQuery;
import com.qcadoo.model.internal.search.SearchResultImpl;
import com.qcadoo.model.internal.utils.EntitySignature;
import com.qcadoo.tenant.api.Standalone;

@Service
@Standalone
public class DataAccessServiceImpl implements DataAccessService {

    private static final String L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN = "DataDefinition belongs to disabled plugin";

    private static final String L_DATA_DEFINITION_MUST_BE_GIVEN = "DataDefinition must be given";

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private HibernateService hibernateService;

    private static final Logger LOG = LoggerFactory.getLogger(DataAccessServiceImpl.class);

    @Auditable
    @Override
    @Transactional
    @Monitorable
    public Entity save(final InternalDataDefinition dataDefinition, final Entity genericEntity) {
        Set<Entity> newlySavedEntities = new HashSet<Entity>();

        Entity resultEntity = performSave(dataDefinition, genericEntity, new HashSet<Entity>(), newlySavedEntities);
        try {
            if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
                resultEntity.setNotValid();
                for (Entity e : newlySavedEntities) {
                    e.setId(null);
                }
            }
        } catch (NoTransactionException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultEntity;
    }

    @SuppressWarnings("unchecked")
    @Monitorable
    private Entity performSave(final InternalDataDefinition dataDefinition, final Entity genericEntity,
            final Set<Entity> alreadySavedEntities, final Set<Entity> newlySavedEntities) {

        checkNotNull(dataDefinition, L_DATA_DEFINITION_MUST_BE_GIVEN);
        checkState(dataDefinition.isEnabled(), L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN);
        checkNotNull(genericEntity, "Entity must be given");

        if (alreadySavedEntities.contains(genericEntity)) {
            return genericEntity;
        }
        Entity genericEntityToSave = genericEntity.copy();

        Object existingDatabaseEntity = getExistingDatabaseEntity(dataDefinition, genericEntity);

        Entity existingGenericEntity = null;

        if (existingDatabaseEntity != null) {
            existingGenericEntity = entityService.convertToGenericEntity(dataDefinition, existingDatabaseEntity);
        }

        validationService.validateGenericEntity(dataDefinition, genericEntity, existingGenericEntity);

        if (!genericEntity.isValid()) {
            copyValidationErrors(dataDefinition, genericEntityToSave, genericEntity);
            if (existingGenericEntity != null) {
                copyMissingFields(genericEntityToSave, existingGenericEntity);
            }
            logValidationErrors(genericEntityToSave);
            return genericEntityToSave;
        }
        Object databaseEntity = entityService.convertToDatabaseEntity(dataDefinition, genericEntity, existingDatabaseEntity);

        if (genericEntity.getId() == null) {
            priorityService.prioritizeEntity(dataDefinition, databaseEntity);
        }

        saveDatabaseEntity(dataDefinition, databaseEntity);

        Entity savedEntity = entityService.convertToGenericEntity(dataDefinition, databaseEntity);

        for (Entry<String, FieldDefinition> fieldEntry : dataDefinition.getFields().entrySet()) {
            if (fieldEntry.getValue().getType() instanceof HasManyType) {
                List<Entity> entities = (List<Entity>) genericEntity.getField(fieldEntry.getKey());

                HasManyType hasManyType = (HasManyType) fieldEntry.getValue().getType();

                if (entities == null || entities instanceof EntityListImpl) {
                    savedEntity.setField(fieldEntry.getKey(), entities);
                    continue;
                }

                List<Entity> savedEntities = saveHasManyEntities(alreadySavedEntities, newlySavedEntities,
                        hasManyType.getJoinFieldName(), savedEntity.getId(), entities,
                        (InternalDataDefinition) hasManyType.getDataDefinition());

                EntityList dbEntities = savedEntity.getHasManyField(fieldEntry.getKey());
                EntityOpResult results = removeOrphans(hasManyType, findOrphans(savedEntities, dbEntities));
                if (!results.isSuccessfull()) {
                    copyValidationErrors(dataDefinition, savedEntity, results.getMessagesHolder());
                    savedEntity.setField(fieldEntry.getKey(), existingGenericEntity.getField(fieldEntry.getKey()));
                    return savedEntity;
                }
                savedEntity.setField(fieldEntry.getKey(), savedEntities);
            } else if (fieldEntry.getValue().getType() instanceof TreeType) {
                List<Entity> entities = (List<Entity>) genericEntity.getField(fieldEntry.getKey());

                if (entities == null || entities instanceof EntityTreeImpl) {
                    savedEntity.setField(fieldEntry.getKey(), entities);
                    continue;
                }

                TreeType treeType = (TreeType) fieldEntry.getValue().getType();

                List<Entity> savedEntities = saveTreeEntities(alreadySavedEntities, newlySavedEntities,
                        treeType.getJoinFieldName(), savedEntity.getId(), entities,
                        (InternalDataDefinition) treeType.getDataDefinition(), null);

                savedEntity.setField(fieldEntry.getKey(), savedEntities);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(savedEntity + " has been saved");
        }

        alreadySavedEntities.add(savedEntity);

        if (genericEntity.getId() == null && savedEntity.getId() != null) {
            newlySavedEntities.add(savedEntity);
        }

        return savedEntity;
    }

    private void logDeletionErrors(final Entity entity) {
        logEntityErrors(entity, entity + " hasn't been deleted, bacause of onDelete hook rejection");
    }

    private void logValidationErrors(final Entity entity) {
        logEntityErrors(entity, entity + " hasn't been saved, bacause of validation errors");
    }

    private void logEntityErrors(final Entity entity, final String msg) {
        if (!LOG.isDebugEnabled()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(msg)) {
            sb.append(msg);
            sb.append('\n');
        }
        for (ErrorMessage error : entity.getGlobalErrors()) {
            sb.append(" --- " + error.getMessage());
            sb.append('\n');
        }
        for (Map.Entry<String, ErrorMessage> error : entity.getErrors().entrySet()) {
            sb.append(" --- " + error.getKey() + ": " + error.getValue().getMessage());
            sb.append('\n');
        }
        LOG.info(sb.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public Object convertToDatabaseEntity(final Entity entity) {
        return entityService.convertToDatabaseEntity((InternalDataDefinition) entity.getDataDefinition(), entity, null);
    }

    private List<Entity> saveHasManyEntities(final Set<Entity> alreadySavedEntities, final Set<Entity> newlySavedEntities,
            final String joinFieldName, final Long id, final List<Entity> entities, final InternalDataDefinition dataDefinition) {
        List<Entity> savedEntities = new ArrayList<Entity>();

        for (Entity innerEntity : entities) {
            innerEntity.setField(joinFieldName, id);
            Entity savedInnerEntity = performSave(dataDefinition, innerEntity, alreadySavedEntities, newlySavedEntities);
            savedEntities.add(savedInnerEntity);
            if (!savedInnerEntity.isValid()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return savedEntities;
    }

    @SuppressWarnings("unchecked")
    private List<Entity> saveTreeEntities(final Set<Entity> alreadySavedEntities, final Set<Entity> newlySavedEntities,
            final String joinFieldName, final Long id, final List<Entity> entities, final InternalDataDefinition dataDefinition,
            final Long parentId) {
        List<Entity> savedEntities = new ArrayList<Entity>();
        int i = 0;

        for (Entity innerEntity : entities) {
            innerEntity.setField(joinFieldName, id);
            innerEntity.setField("parent", parentId);
            innerEntity.setField("priority", ++i);
            List<Entity> children = (List<Entity>) innerEntity.getField("children");
            innerEntity.setField("children", null);
            Entity savedInnerEntity = performSave(dataDefinition, innerEntity, alreadySavedEntities, newlySavedEntities);
            savedEntities.add(savedInnerEntity);
            if (children != null) {
                children = saveTreeEntities(alreadySavedEntities, newlySavedEntities, joinFieldName, id, children,
                        dataDefinition, savedInnerEntity.getId());
                savedInnerEntity.setField("children", children);
            }
            if (!savedInnerEntity.isValid()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return savedEntities;
    }

    private EntityOpResult removeOrphans(final CollectionFieldType fieldType, final Iterable<Entity> orphans) {
        switch (fieldType.getCascade()) {
            case NULLIFY:
                return nullifyOrphans(fieldType.getJoinFieldName(), orphans);
            case DELETE:
                return deleteOrphans((InternalDataDefinition) fieldType.getDataDefinition(), orphans);
            default:
                throw new IllegalArgumentException(String.format("Unsupported cascade value '%s'", fieldType.getCascade()));
        }
    }

    private EntityOpResult deleteOrphans(final InternalDataDefinition dataDefinition, final Iterable<Entity> orphans) {
        checkNotNull(dataDefinition, L_DATA_DEFINITION_MUST_BE_GIVEN);
        checkState(dataDefinition.isDeletable(), "Entity must be deletable");
        checkState(dataDefinition.isEnabled(), L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN);

        for (Entity orphan : orphans) {
            EntityOpResult result = deleteEntity(dataDefinition, orphan.getId());
            if (!result.isSuccessfull()) {
                return result;
            }
        }
        return EntityOpResult.successfull();
    }

    private EntityOpResult nullifyOrphans(final String fieldName, final Iterable<Entity> entities) {
        for (Entity entity : entities) {
            entity.setField(fieldName, null);
            Entity savedEntity = entity.getDataDefinition().save(entity);
            if (!savedEntity.isValid()) {
                return EntityOpResult.failure(savedEntity);
            }
        }
        return EntityOpResult.successfull();
    }

    private Collection<Entity> findOrphans(final List<Entity> savedEntities, final List<Entity> dbEntities) {
        final Set<Long> savedEntityIds = Sets.newHashSet(EntityUtils.getIdsView(savedEntities));
        return Collections2.filter(dbEntities, new Predicate<Entity>() {

            @Override
            public boolean apply(final Entity entity) {
                return !savedEntityIds.contains(entity.getId());
            }
        });
    }

    @Override
    @Transactional
    @Monitorable
    public List<Entity> activate(final InternalDataDefinition dataDefinition, final Long... entityIds) {
        if (!dataDefinition.isActivable()) {
            return Collections.emptyList();
        }

        List<Entity> activatedEntities = new ArrayList<Entity>();

        for (Long entityId : entityIds) {
            Entity entity = get(dataDefinition, entityId);

            if (entity == null) {
                throw new IllegalStateException("Cannot activate " + entityId);
            }

            if (!entity.isActive()) {
                entity.setActive(true);
                entity = save(dataDefinition, entity);

                if (!entity.isValid()) {
                    throw new IllegalStateException("Cannot activate " + entity);
                }

                LOG.info(entity + " has been activated");

                activatedEntities.add(entity);
            }
        }

        return activatedEntities;
    }

    @Override
    @Transactional
    @Monitorable
    public List<Entity> deactivate(final InternalDataDefinition dataDefinition, final Long... entityIds) {
        if (!dataDefinition.isActivable()) {
            return Collections.emptyList();
        }

        List<Entity> deactivatedEntities = new ArrayList<Entity>();

        for (Long entityId : entityIds) {
            Entity entity = get(dataDefinition, entityId);

            if (entity == null) {
                throw new IllegalStateException("Cannot deactivate " + entityId + " (entity not found)");
            }

            if (entity.isActive()) {
                entity.setActive(false);
                entity = save(dataDefinition, entity);

                if (!entity.isValid()) {
                    throw new IllegalStateException("Cannot deactivate " + entity + " because of validation errors");
                }

                LOG.info(entity + " has been deactivated");

                deactivatedEntities.add(entity);
            }
        }

        return deactivatedEntities;
    }

    @Override
    @Transactional
    @Monitorable
    public List<Entity> copy(final InternalDataDefinition dataDefinition, final Long... entityIds) {
        List<Entity> copiedEntities = new ArrayList<Entity>();
        for (Long entityId : entityIds) {
            Entity sourceEntity = get(dataDefinition, entityId);
            Entity targetEntity = copy(dataDefinition, sourceEntity);

            if (targetEntity == null) {
                throw new IllegalStateException("Cannot copy " + sourceEntity);
            }

            LOG.info(sourceEntity + " has been copied to " + targetEntity);

            targetEntity = save(dataDefinition, targetEntity);

            if (!targetEntity.isValid()) {
                throw new CopyException(targetEntity);
            }

            copiedEntities.add(targetEntity);
        }

        return copiedEntities;
    }

    public Entity copy(final InternalDataDefinition dataDefinition, final Entity sourceEntity) {
        Entity targetEntity = dataDefinition.create();

        for (Entry<String, FieldDefinition> fieldEntry : dataDefinition.getFields().entrySet()) {
            FieldDefinition fieldDefinition = fieldEntry.getValue();
            String fieldName = fieldEntry.getKey();
            boolean copy = fieldDefinition.getType().isCopyable();
            if (copy) {
                targetEntity.setField(fieldName, getCopyValueOfSimpleField(sourceEntity, dataDefinition, fieldName));
            }

        }

        if (!dataDefinition.callCopyHook(targetEntity)) {
            return null;
        }

        for (String fieldName : dataDefinition.getFields().keySet()) {
            copyHasManyField(sourceEntity, targetEntity, dataDefinition, fieldName);
        }

        for (String fieldName : dataDefinition.getFields().keySet()) {
            copyTreeField(sourceEntity, targetEntity, dataDefinition, fieldName);
        }
        for (String fieldName : dataDefinition.getFields().keySet()) {
            copyManyToManyField(sourceEntity, targetEntity, dataDefinition, fieldName);
        }

        return targetEntity;
    }

    private void copyTreeField(final Entity sourceEntity, final Entity targetEntity, final DataDefinition dataDefinition,
            final String fieldName) {
        FieldDefinition fieldDefinition = dataDefinition.getField(fieldName);

        if (!(fieldDefinition.getType() instanceof TreeType) || !((TreeType) fieldDefinition.getType()).isCopyable()) {
            return;
        }

        TreeType treeType = ((TreeType) fieldDefinition.getType());

        List<Entity> entities = new ArrayList<Entity>();

        Entity root = sourceEntity.getTreeField(fieldName).getRoot();

        if (root != null) {
            root.setField(treeType.getJoinFieldName(), null);
            root = copy((InternalDataDefinition) treeType.getDataDefinition(), root);

            if (root != null) {
                entities.add(root);
            }
        }

        targetEntity.setField(fieldName, entities);
    }

    private void copyHasManyField(final Entity sourceEntity, final Entity targetEntity, final DataDefinition dataDefinition,
            final String fieldName) {
        FieldDefinition fieldDefinition = dataDefinition.getField(fieldName);

        if (!(fieldDefinition.getType() instanceof HasManyType) || !((HasManyType) fieldDefinition.getType()).isCopyable()) {
            return;
        }

        HasManyType hasManyType = ((HasManyType) fieldDefinition.getType());

        List<Entity> entities = new ArrayList<Entity>();

        for (Entity childEntity : sourceEntity.getHasManyField(fieldName)) {
            childEntity.setField(hasManyType.getJoinFieldName(), null);

            Entity savedChildEntity = copy((InternalDataDefinition) hasManyType.getDataDefinition(), childEntity);

            if (savedChildEntity != null) {
                entities.add(savedChildEntity);
            }
        }

        targetEntity.setField(fieldName, entities);
    }

    private void copyManyToManyField(final Entity sourceEntity, final Entity targetEntity, final DataDefinition dataDefinition,
            final String fieldName) {
        FieldDefinition fieldDefinition = dataDefinition.getField(fieldName);

        if (!(fieldDefinition.getType() instanceof ManyToManyType) || !((ManyToManyType) fieldDefinition.getType()).isCopyable()) {
            return;
        }
        targetEntity.setField(fieldName, sourceEntity.getField(fieldName));
    }

    private Object getCopyValueOfSimpleField(final Entity sourceEntity, final DataDefinition dataDefinition,
            final String fieldName) {
        InternalFieldDefinition fieldDefinition = (InternalFieldDefinition) dataDefinition.getField(fieldName);
        if (fieldDefinition.isUnique()) {
            if (fieldDefinition.canBeBothCopyableAndUnique()) {
                return getCopyValueOfUniqueField(dataDefinition, fieldDefinition, sourceEntity.getStringField(fieldName));
            } else {
                sourceEntity.addError(fieldDefinition, "qcadooView.validate.field.error.invalidUniqueType");
                throw new CopyException(sourceEntity);
            }
        } else if (fieldDefinition.getType() instanceof HasManyType) {
            return null;
        } else if (fieldDefinition.getType() instanceof TreeType) {
            return null;
        } else if (fieldDefinition.getType() instanceof ManyToManyType) {
            return null;
        } else {
            return sourceEntity.getField(fieldName);
        }
    }

    private String getCopyValueOfUniqueField(final DataDefinition dataDefinition, final FieldDefinition fieldDefinition,
            final String value) {
        if (value == null) {
            return value;
        } else {
            Matcher matcher = Pattern.compile("(.+)\\((\\d+)\\)").matcher(value);

            String oldValue = value;
            int index = 1;

            if (matcher.matches()) {
                oldValue = matcher.group(1);
                index = Integer.valueOf(matcher.group(2)) + 1;
            }

            while (true) {
                String newValue = oldValue + "(" + (index++) + ")";

                int matches = dataDefinition.find().setMaxResults(1)
                        .add(SearchRestrictions.eq(fieldDefinition.getName(), newValue)).list().getTotalNumberOfEntities();

                if (matches == 0) {
                    return newValue;
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public Entity get(final InternalDataDefinition dataDefinition, final Long entityId) {
        checkNotNull(dataDefinition, L_DATA_DEFINITION_MUST_BE_GIVEN);
        checkState(dataDefinition.isEnabled(), L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN);
        checkNotNull(entityId, "EntityId must be given");

        Object databaseEntity = getDatabaseEntity(dataDefinition, entityId);

        if (databaseEntity == null) {
            logEntityInfo(dataDefinition, entityId, "hasn't been retrieved, because it doesn't exist");
            return null;
        }

        Entity entity = entityService.convertToGenericEntity(dataDefinition, databaseEntity);

        if (LOG.isDebugEnabled()) {
            LOG.debug(entity + " has been retrieved");
        }

        return entity;
    }

    @Override
    @Transactional
    @Monitorable
    public EntityOpResult delete(final InternalDataDefinition dataDefinition, final Long... entityIds) {
        checkNotNull(dataDefinition, L_DATA_DEFINITION_MUST_BE_GIVEN);
        checkState(dataDefinition.isDeletable(), "Entity must be deletable");
        checkState(dataDefinition.isEnabled(), L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN);
        checkState(entityIds.length > 0, "EntityIds must be given");

        for (Long entityId : entityIds) {
            EntityOpResult result = deleteEntity(dataDefinition, entityId);
            if (!result.isSuccessfull()) {
                return result;
            }
        }
        return EntityOpResult.successfull();
    }

    @Override
    public InternalDataDefinition getDataDefinition(final String pluginIdentifier, final String name) {
        InternalDataDefinition dataDefinition = (InternalDataDefinition) dataDefinitionService.get(pluginIdentifier, name);

        if (dataDefinition == null) {
            throw new IllegalStateException("DataDefinition " + pluginIdentifier + "_" + name + " cannot be found");
        } else if (!dataDefinition.isEnabled()) {
            throw new IllegalStateException("DataDefinition " + dataDefinition + " belongs to disabled plugin");
        }

        return dataDefinition;
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public SearchResult find(final SearchQuery searchQuery) {
        checkArgument(searchQuery != null, "SearchCriteria must be given");

        Query query = searchQuery.createQuery(hibernateService.getCurrentSession());
        searchQuery.addParameters(query);

        int totalNumberOfEntities = -1;

        if (searchQuery.hasFirstAndMaxResults()) {
            totalNumberOfEntities = hibernateService.list(query).size();
            searchQuery.addFirstAndMaxResults(query);
        }

        if (totalNumberOfEntities == 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("There is no entity matching criteria " + searchQuery);
            }
            return getResultSet(null, totalNumberOfEntities, Collections.emptyList());
        }

        List<?> results = hibernateService.list(query);

        if (totalNumberOfEntities == -1) {
            totalNumberOfEntities = results.size();

            if (totalNumberOfEntities == 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("There is no entity matching criteria " + searchQuery);
                }
                return getResultSet(null, totalNumberOfEntities, Collections.emptyList());
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("There are " + totalNumberOfEntities + " entities matching criteria " + searchQuery);
        }

        InternalDataDefinition searchQueryDataDefinition = (InternalDataDefinition) searchQuery.getDataDefinition();

        if (searchQueryDataDefinition == null) {
            searchQueryDataDefinition = hibernateService.resolveDataDefinition(query);
        }

        return getResultSet(searchQueryDataDefinition, totalNumberOfEntities, results);
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public SearchResult find(final SearchCriteria searchCriteria) {
        checkArgument(searchCriteria != null, "SearchCriteria must be given");

        Criteria criteria = searchCriteria.createCriteria(hibernateService.getCurrentSession());

        int totalNumberOfEntities = hibernateService.getTotalNumberOfEntities(criteria);

        if (totalNumberOfEntities == 0) {
            LOG.info("There is no entity matching criteria " + searchCriteria);
            return getResultSet(null, totalNumberOfEntities, Collections.emptyList());
        }

        searchCriteria.addFirstAndMaxResults(criteria);
        searchCriteria.addOrders(criteria);
        searchCriteria.addCacheable(criteria);

        List<?> results = hibernateService.list(criteria);

        if (LOG.isDebugEnabled()) {
            LOG.debug("There are " + totalNumberOfEntities + " entities matching criteria " + searchCriteria);
        }

        InternalDataDefinition searchQueryDataDefinition = (InternalDataDefinition) searchCriteria.getDataDefinition();

        if (searchQueryDataDefinition == null) {
            searchQueryDataDefinition = hibernateService.resolveDataDefinition(criteria);
        }

        return getResultSet(searchQueryDataDefinition, totalNumberOfEntities, results);
    }

    @Override
    public void moveTo(final InternalDataDefinition dataDefinition, final Long entityId, final int position) {
        checkState(position > 0, "Position must be greaten than 0");
        move(dataDefinition, entityId, position, 0);
    }

    @Override
    public void move(final InternalDataDefinition dataDefinition, final Long entityId, final int offset) {
        checkState(offset != 0, "Offset must be different than 0");
        move(dataDefinition, entityId, 0, offset);
    }

    @Transactional
    @Monitorable
    private void move(final InternalDataDefinition dataDefinition, final Long entityId, final int position, final int offset) {
        checkNotNull(dataDefinition, L_DATA_DEFINITION_MUST_BE_GIVEN);
        checkState(dataDefinition.isPrioritizable(), "Entity must be prioritizable");
        checkState(dataDefinition.isEnabled(), L_DATA_DEFINITION_BELONGS_TO_DISABLED_PLUGIN);
        checkNotNull(entityId, "EntityId must be given");

        Object databaseEntity = getDatabaseEntity(dataDefinition, entityId);
        if (databaseEntity == null) {
            logEntityInfo(dataDefinition, entityId, "hasn't been prioritized, because it doesn't exist");
            return;
        }

        priorityService.move(dataDefinition, databaseEntity, position, offset);
        logEntityInfo(dataDefinition, entityId, "has been prioritized");
    }

    private Object getExistingDatabaseEntity(final InternalDataDefinition dataDefinition, final Entity entity) {
        Object existingDatabaseEntity = null;

        if (entity.getId() != null) {
            existingDatabaseEntity = getDatabaseEntity(dataDefinition, entity.getId());
            checkState(existingDatabaseEntity != null, "Entity[%s][id=%s] cannot be found", dataDefinition.getPluginIdentifier()
                    + "." + dataDefinition.getName(), entity.getId());
        }

        return existingDatabaseEntity;
    }

    private EntityOpResult deleteEntity(final InternalDataDefinition dataDefinition, final Long entityId) {
        return deleteEntity(dataDefinition, entityId, Sets.<EntitySignature> newHashSet());
    }

    private EntityOpResult deleteEntity(final InternalDataDefinition dataDefinition, final Long entityId,
            final Set<EntitySignature> traversedEntities) {
        return deleteEntity(dataDefinition, entityId, false, traversedEntities);
    }

    private EntityOpResult deleteEntity(final InternalDataDefinition dataDefinition, final Long entityId, final boolean testOnly,
            final Set<EntitySignature> traversedEntities) {

        Object databaseEntity = getDatabaseEntity(dataDefinition, entityId);

        checkNotNull(databaseEntity, "Entity[%s][id=%s] cannot be found", dataDefinition.getPluginIdentifier() + "."
                + dataDefinition.getName(), entityId);

        Entity entity = get(dataDefinition, entityId);

        if (!dataDefinition.callDeleteHook(entity)) {
            logDeletionErrors(entity);
            entity.addGlobalError("qcadooView.message.deleteFailedMessage");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return EntityOpResult.failure(entity);
        }

        priorityService.deprioritizeEntity(dataDefinition, databaseEntity);

        Map<String, FieldDefinition> fields = dataDefinition.getFields();
        for (FieldDefinition fieldDefinition : fields.values()) {
            if (fieldDefinition.getType() instanceof CollectionFieldType) {
                CollectionFieldType collectionFieldType = (CollectionFieldType) fieldDefinition.getType();
                @SuppressWarnings("unchecked")
                Collection<Entity> children = (Collection<Entity>) entity.getField(fieldDefinition.getName());
                EntityOpResult cascadeDeletionRes = performCascadeStrategy(entity, collectionFieldType, children,
                        traversedEntities);
                if (!cascadeDeletionRes.isSuccessfull()) {
                    return cascadeDeletionRes;
                }
            }
        }

        if (testOnly) {
            logEntityInfo(dataDefinition, entityId, "may be cascade deleted");
        } else {
            try {
                databaseEntity = getDatabaseEntity(dataDefinition, entityId);
                if (databaseEntity != null) {
                    hibernateService.getCurrentSession().delete(databaseEntity);
                    hibernateService.getCurrentSession().flush();
                }
            } catch (ConstraintViolationException e) {
                throw new IllegalStateException(getConstraintViolationMessage(entity), e);
            }
            logEntityInfo(dataDefinition, entityId, "has been deleted");
        }
        return new EntityOpResult(true, entity);
    }

    private EntityOpResult performCascadeStrategy(final Entity entity, final FieldType fieldType,
            final Collection<Entity> children, final Set<EntitySignature> traversedEntities) {
        if (children == null || children.isEmpty()) {
            return EntityOpResult.successfull();
        }
        boolean isManyToManyType = fieldType instanceof ManyToManyType;
        InternalDataDefinition childDataDefinition = (InternalDataDefinition) ((DataDefinitionHolder) fieldType)
                .getDataDefinition();
        Cascadeable.Cascade cascade = ((Cascadeable) fieldType).getCascade();

        if (Cascadeable.Cascade.NULLIFY.equals(cascade)) {
            if (!isManyToManyType) {
                return performCascadeNullification(childDataDefinition, children, entity, fieldType);
            }
            return EntityOpResult.successfull();
        } else if (Cascadeable.Cascade.DELETE.equals(cascade)) {
            return performCascadeDelete(childDataDefinition, children, isManyToManyType, traversedEntities);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported cascade value '%s'", cascade));
        }
    }

    private EntityOpResult performCascadeNullification(final InternalDataDefinition childDataDefinition,
            final Collection<Entity> children, final Entity entity, final FieldType fieldType) {
        String joinFieldName = ((JoinFieldHolder) fieldType).getJoinFieldName();
        for (Entity child : children) {
            child.setField(joinFieldName, null);
            child = save(childDataDefinition, child);
            if (!child.isValid()) {
                String msg = String.format("Can not nullify field '%s' in %s because of following validation errors:",
                        joinFieldName, child);
                logEntityErrors(child, msg);
                return EntityOpResult.failure(child);
            }
        }
        return EntityOpResult.successfull();
    }

    private EntityOpResult performCascadeDelete(final InternalDataDefinition childDataDefinition,
            final Collection<Entity> children, final boolean testOnly, final Set<EntitySignature> traversedEntities) {
        for (Entity child : children) {
            EntitySignature childSignature = EntitySignature.of(child);
            if (!traversedEntities.contains(childSignature)) {
                traversedEntities.add(childSignature);
                EntityOpResult result = deleteEntity(childDataDefinition, child.getId(), testOnly, traversedEntities);
                if (!result.isSuccessfull()) {
                    return result;
                }
            }
        }
        return EntityOpResult.successfull();
    }

    private String getConstraintViolationMessage(final Entity entity) {
        String message = null;
        try {
            message = String.format("Entity [ENTITY.%s] is in use", getIdentifierExpression(entity));
        } catch (Exception e) {
            message = "Entity is in use";
        }
        return message;
    }

    private String getIdentifierExpression(final Entity entity) {
        InternalDataDefinition dataDef = (InternalDataDefinition) entity.getDataDefinition();
        return expressionService.getValue(entity, dataDef.getIdentifierExpression(), Locale.ENGLISH);
    }

    private SearchResultImpl getResultSet(final InternalDataDefinition dataDefinition, final int totalNumberOfEntities,
            final List<?> results) {
        List<Entity> genericResults = new ArrayList<Entity>();

        for (Object databaseEntity : results) {
            genericResults.add(entityService.convertToGenericEntity(dataDefinition, databaseEntity));
        }

        SearchResultImpl resultSet = new SearchResultImpl();
        resultSet.setResults(genericResults);
        resultSet.setTotalNumberOfEntities(totalNumberOfEntities);

        return resultSet;
    }

    protected Object getDatabaseEntity(final InternalDataDefinition dataDefinition, final Long entityId) {
        return hibernateService.getCurrentSession().get(dataDefinition.getClassForEntity(), entityId);
    }

    protected void saveDatabaseEntity(final InternalDataDefinition dataDefinition, final Object databaseEntity) {
        hibernateService.getCurrentSession().save(databaseEntity);
    }

    private void copyMissingFields(final Entity genericEntityToSave, final Entity existingGenericEntity) {
        for (Map.Entry<String, Object> field : existingGenericEntity.getFields().entrySet()) {
            if (!genericEntityToSave.getFields().containsKey(field.getKey())) {
                genericEntityToSave.setField(field.getKey(), field.getValue());
            }
        }
    }

    private void copyValidationErrors(final DataDefinition dataDefinition, final EntityMessagesHolder target,
            final EntityMessagesHolder source) {
        for (ErrorMessage error : source.getGlobalErrors()) {
            target.addGlobalError(error.getMessage(), error.getVars());
        }
        for (Map.Entry<String, ErrorMessage> error : source.getErrors().entrySet()) {
            target.addError(dataDefinition.getField(error.getKey()), error.getValue().getMessage(), error.getValue().getVars());
        }
    }

    private void logEntityInfo(final DataDefinition dataDefinition, final Long entityId, final String message) {
        if (LOG.isInfoEnabled()) {
            StringBuilder entityInfo = new StringBuilder("Entity[");
            entityInfo.append(dataDefinition.getPluginIdentifier()).append('.').append(dataDefinition.getName());
            entityInfo.append("][id=").append(entityId).append("] ");
            entityInfo.append(message);
            LOG.info(entityInfo.toString());
        }
    }

    protected void setEntityService(final EntityService entityService) {
        this.entityService = entityService;
    }

    protected void setExpressionService(final ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    protected void setPriorityService(final PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    protected void setValidationService(final ValidationService validationService) {
        this.validationService = validationService;
    }

    protected void setHibernateService(final HibernateService hibernateService) {
        this.hibernateService = hibernateService;
    }

}
