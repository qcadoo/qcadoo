/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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
package com.qcadoo.model.internal.definitionconverter;

import static com.google.common.base.Preconditions.checkState;
import static com.qcadoo.model.internal.AbstractModelXmlConverter.FieldsTag.PRIORITY;
import static com.qcadoo.model.internal.AbstractModelXmlConverter.OtherTag.IDENTIFIER;
import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DictionaryService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.api.types.HasManyType;
import com.qcadoo.model.api.types.ManyToManyType;
import com.qcadoo.model.api.types.TreeType;
import com.qcadoo.model.internal.AbstractModelXmlConverter;
import com.qcadoo.model.internal.DataDefinitionImpl;
import com.qcadoo.model.internal.FieldDefinitionImpl;
import com.qcadoo.model.internal.api.DataAccessService;
import com.qcadoo.model.internal.api.EntityHookDefinition;
import com.qcadoo.model.internal.api.ErrorMessageDefinition;
import com.qcadoo.model.internal.api.FieldHookDefinition;
import com.qcadoo.model.internal.api.InternalDataDefinitionService;
import com.qcadoo.model.internal.api.ModelXmlToDefinitionConverter;
import com.qcadoo.model.internal.hooks.EntityHookDefinitionImpl;
import com.qcadoo.model.internal.hooks.FieldHookDefinitionImpl;
import com.qcadoo.model.internal.hooks.HookInitializationException;
import com.qcadoo.model.internal.types.BelongsToEntityType;
import com.qcadoo.model.internal.types.BooleanType;
import com.qcadoo.model.internal.types.DateTimeType;
import com.qcadoo.model.internal.types.DateType;
import com.qcadoo.model.internal.types.DecimalType;
import com.qcadoo.model.internal.types.DictionaryType;
import com.qcadoo.model.internal.types.EnumType;
import com.qcadoo.model.internal.types.FileType;
import com.qcadoo.model.internal.types.HasManyEntitiesType;
import com.qcadoo.model.internal.types.IntegerType;
import com.qcadoo.model.internal.types.ManyToManyEntitiesType;
import com.qcadoo.model.internal.types.PasswordType;
import com.qcadoo.model.internal.types.PriorityType;
import com.qcadoo.model.internal.types.StringType;
import com.qcadoo.model.internal.types.TextType;
import com.qcadoo.model.internal.types.TreeEntitiesType;
import com.qcadoo.model.internal.utils.ClassNameUtils;
import com.qcadoo.model.internal.validators.CustomEntityValidator;
import com.qcadoo.model.internal.validators.CustomValidator;
import com.qcadoo.model.internal.validators.LengthValidator;
import com.qcadoo.model.internal.validators.RangeValidator;
import com.qcadoo.model.internal.validators.RegexValidator;
import com.qcadoo.model.internal.validators.RequiredValidator;
import com.qcadoo.model.internal.validators.ScaleValidator;
import com.qcadoo.model.internal.validators.UniqueValidator;
import com.qcadoo.model.internal.validators.UnscaledValueValidator;

@Service
public final class ModelXmlToDefinitionConverterImpl extends AbstractModelXmlConverter implements ModelXmlToDefinitionConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ModelXmlToDefinitionConverterImpl.class);

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private InternalDataDefinitionService dataDefinitionService;

    @Autowired
    private DataAccessService dataAccessService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TranslationService translationService;

    @Transactional
    @Override
    public Collection<DataDefinition> convert(final Resource... resources) {
        List<DataDefinition> dataDefinitions = new ArrayList<DataDefinition>();

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                LOG.info("Creating dataDefinition from " + resource);

                try {
                    dataDefinitions.add(parse(resource.getInputStream()));
                } catch (HookInitializationException e) {
                    throw new IllegalStateException("Error while parsing model.xml: " + e.getMessage(), e);
                } catch (IOException e) {
                    throw new IllegalStateException("Error while parsing model.xml: " + e.getMessage(), e);
                } catch (ModelXmlParsingException e) {
                    throw new IllegalStateException("Error while parsing model.xml: " + e.getMessage(), e);
                } catch (XMLStreamException e) {
                    throw new IllegalStateException("Error while parsing model.xml: " + e.getMessage(), e);
                } catch (javax.xml.stream.FactoryConfigurationError e) {
                    throw new IllegalStateException("Error while parsing model.xml: " + e.getMessage(), e);
                }
            }
        }

        return dataDefinitions;
    }

    private DataDefinition parse(final InputStream stream) throws HookInitializationException, ModelXmlParsingException,
            XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
        DataDefinition dataDefinition = null;

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                dataDefinition = getDataDefinition(reader, getPluginIdentifier(reader));
                break;
            }
        }

        reader.close();

        return dataDefinition;
    }

    private DataDefinition getDataDefinition(final XMLStreamReader reader, final String pluginIdentifier)
            throws XMLStreamException, HookInitializationException, ModelXmlParsingException {
        DataDefinitionImpl dataDefinition = getModelDefinition(reader, pluginIdentifier);

        LOG.info("Creating dataDefinition " + dataDefinition);

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagEnded(reader, TAG_MODEL)) {
                break;
            }

            if (TAG_FIELDS.equals(getTagStarted(reader))) {
                while (reader.hasNext() && reader.next() > 0) {
                    if (isTagEnded(reader, TAG_FIELDS)) {
                        break;
                    }

                    String tag = getTagStarted(reader);

                    if (tag == null) {
                        continue;
                    }

                    addFieldElement(reader, dataDefinition, tag);
                }
            }

            if (dataDefinition.isAuditable()) {
                dataDefinition.withField(getAuditFieldDefinition(dataDefinition, "createDate", new DateTimeType()));
                dataDefinition.withField(getAuditFieldDefinition(dataDefinition, "updateDate", new DateTimeType()));
                dataDefinition.withField(getAuditFieldDefinition(dataDefinition, "createUser", new StringType()));
                dataDefinition.withField(getAuditFieldDefinition(dataDefinition, "updateUser", new StringType()));
            }

            if (TAG_HOOKS.equals(getTagStarted(reader))) {
                while (reader.hasNext() && reader.next() > 0) {
                    if (isTagEnded(reader, TAG_HOOKS)) {
                        break;
                    }

                    String tag = getTagStarted(reader);

                    if (tag == null) {
                        continue;
                    }

                    addHookElement(reader, dataDefinition, tag);
                }
            }

            String tag = getTagStarted(reader);

            if (tag == null) {
                continue;
            }

            addOtherElement(reader, dataDefinition, tag);
        }

        for (EntityHookDefinition hook : dataDefinition.getViewHooks()) {
            hook.initialize(dataDefinition);
        }

        for (EntityHookDefinition hook : dataDefinition.getCopyHooks()) {
            hook.initialize(dataDefinition);
        }

        for (EntityHookDefinition hook : dataDefinition.getSaveHooks()) {
            hook.initialize(dataDefinition);
        }

        for (EntityHookDefinition hook : dataDefinition.getUpdateHooks()) {
            hook.initialize(dataDefinition);
        }

        for (EntityHookDefinition hook : dataDefinition.getCreateHooks()) {
            hook.initialize(dataDefinition);
        }

        for (EntityHookDefinition hook : dataDefinition.getValidators()) {
            hook.initialize(dataDefinition);
        }

        for (FieldDefinition field : dataDefinition.getFields().values()) {
            for (FieldHookDefinition hook : ((FieldDefinitionImpl) field).getValidators()) {
                hook.initialize(dataDefinition, field);
            }
        }

        dataDefinitionService.save(dataDefinition);

        return dataDefinition;
    }

    private void addHookElement(final XMLStreamReader reader, final DataDefinitionImpl dataDefinition, final String tag)
            throws XMLStreamException, HookInitializationException, ModelXmlParsingException {
        HooksTag hooksTag = HooksTag.valueOf(tag.toUpperCase(Locale.ENGLISH));

        switch (hooksTag) {
            case ONVIEW:
                dataDefinition.addViewHook(getHookDefinition(reader));
                break;
            case ONCREATE:
                dataDefinition.addCreateHook(getHookDefinition(reader));
                break;
            case ONUPDATE:
                dataDefinition.addUpdateHook(getHookDefinition(reader));
                break;
            case ONSAVE:
                dataDefinition.addSaveHook(getHookDefinition(reader));
                break;
            case ONCOPY:
                dataDefinition.addCopyHook(getHookDefinition(reader));
                break;
            case VALIDATESWITH:
                dataDefinition.addValidatorHook(new CustomEntityValidator(getHookDefinition(reader)));
                break;
            default:
                throw new ModelXmlParsingException("Illegal type of model's hook '" + hooksTag + "'");
        }
    }

    private void addOtherElement(final XMLStreamReader reader, final DataDefinitionImpl dataDefinition, final String tag)
            throws XMLStreamException {
        OtherTag otherTag = OtherTag.valueOf(tag.toUpperCase(Locale.ENGLISH));
        if (otherTag == IDENTIFIER) {
            dataDefinition.setIdentifierExpression(getIdentifierExpression(reader));
        }
    }

    private void addFieldElement(final XMLStreamReader reader, final DataDefinitionImpl dataDefinition, final String tag)
            throws XMLStreamException, HookInitializationException, ModelXmlParsingException {
        FieldsTag fieldTag = FieldsTag.valueOf(tag.toUpperCase(Locale.ENGLISH));
        if (fieldTag == PRIORITY) {
            dataDefinition.addPriorityField(getPriorityFieldDefinition(reader, dataDefinition));
        } else {
            dataDefinition.withField(getFieldDefinition(reader, dataDefinition, fieldTag));
        }
    }

    private DataDefinitionImpl getModelDefinition(final XMLStreamReader reader, final String pluginIdentifier) {
        String modelName = getStringAttribute(reader, "name");

        LOG.info("Reading model " + modelName + " for plugin " + pluginIdentifier);

        DataDefinitionImpl dataDefinition = new DataDefinitionImpl(pluginIdentifier, modelName, dataAccessService);
        dataDefinition.setDeletable(getBooleanAttribute(reader, "deletable", true));
        dataDefinition.setInsertable(getBooleanAttribute(reader, "insertable", true));
        dataDefinition.setUpdatable(getBooleanAttribute(reader, "updatable", true));
        dataDefinition.setActivable(getBooleanAttribute(reader, "activable", false));
        dataDefinition.setAuditable(getBooleanAttribute(reader, "auditable", false));
        dataDefinition.setFullyQualifiedClassName(ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName));
        return dataDefinition;
    }

    private FieldType getDictionaryType(final XMLStreamReader reader) {
        String dictionaryName = getStringAttribute(reader, "dictionary");
        checkState(hasText(dictionaryName), "Dictionary name is required");
        return new DictionaryType(dictionaryName, dictionaryService);
    }

    private FieldType getEnumType(final XMLStreamReader reader, final String translationPath) throws XMLStreamException {
        String values = getStringAttribute(reader, "values");
        if (values.trim().length() == 0) {
            return new EnumType(translationService, translationPath);
        }
        return new EnumType(translationService, translationPath, values.split(","));
    }

    private FieldType getHasManyType(final XMLStreamReader reader, final String pluginIdentifier) {
        String plugin = getStringAttribute(reader, "plugin");
        HasManyType.Cascade cascade = "delete".equals(getStringAttribute(reader, "cascade")) ? HasManyType.Cascade.DELETE
                : HasManyType.Cascade.NULLIFY;
        return new HasManyEntitiesType(plugin == null ? pluginIdentifier : plugin, getStringAttribute(reader, TAG_MODEL),
                getStringAttribute(reader, "joinField"), cascade, getBooleanAttribute(reader, "copyable", false),
                dataDefinitionService);
    }

    private FieldType getManyToManyType(final XMLStreamReader reader, final String pluginIdentifier) {
        String plugin = getStringAttribute(reader, "plugin");
        ManyToManyType.Cascade cascade = "delete".equals(getStringAttribute(reader, "cascade")) ? ManyToManyType.Cascade.DELETE
                : ManyToManyType.Cascade.NULLIFY;
        return new ManyToManyEntitiesType(plugin == null ? pluginIdentifier : plugin, getStringAttribute(reader, TAG_MODEL),
                getStringAttribute(reader, "joinField"), cascade, getBooleanAttribute(reader, "copyable", false),
                dataDefinitionService);
    }

    private FieldType getTreeType(final XMLStreamReader reader, final String pluginIdentifier) {
        String plugin = getStringAttribute(reader, "plugin");
        TreeType.Cascade cascade = "delete".equals(getStringAttribute(reader, "cascade")) ? TreeType.Cascade.DELETE
                : TreeType.Cascade.NULLIFY;
        return new TreeEntitiesType(plugin == null ? pluginIdentifier : plugin, getStringAttribute(reader, TAG_MODEL),
                getStringAttribute(reader, "joinField"), cascade, getBooleanAttribute(reader, "copyable", false),
                dataDefinitionService);
    }

    private FieldType getBelongsToType(final XMLStreamReader reader, final String pluginIdentifier) {
        boolean lazy = getBooleanAttribute(reader, "lazy", true);
        String plugin = getStringAttribute(reader, "plugin");
        String modelName = getStringAttribute(reader, TAG_MODEL);
        if (lazy) {
            return new BelongsToEntityType(plugin == null ? pluginIdentifier : plugin, modelName, dataDefinitionService, true);
        } else {
            return new BelongsToEntityType(plugin == null ? pluginIdentifier : plugin, modelName, dataDefinitionService, false);
        }
    }

    private FieldDefinition getAuditFieldDefinition(final DataDefinitionImpl dataDefinition, final String name,
            final FieldType type) {
        FieldDefinitionImpl fieldDefinition = new FieldDefinitionImpl(dataDefinition, name);
        fieldDefinition.withReadOnly(false);
        fieldDefinition.setPersistent(true);
        fieldDefinition.withType(type);
        return fieldDefinition;
    }

    private FieldDefinition getFieldDefinition(final XMLStreamReader reader, final DataDefinitionImpl dataDefinition,
            final FieldsTag fieldTag) throws XMLStreamException, HookInitializationException, ModelXmlParsingException {
        String fieldType = reader.getLocalName();
        String name = getStringAttribute(reader, "name");
        FieldDefinitionImpl fieldDefinition = new FieldDefinitionImpl(dataDefinition, name);
        fieldDefinition.withReadOnly(getBooleanAttribute(reader, "readonly", false));
        fieldDefinition.withDefaultValue(getStringAttribute(reader, "default"));
        fieldDefinition.setPersistent(getBooleanAttribute(reader, "persistent", true));
        fieldDefinition.setExpression(getStringAttribute(reader, "expression"));
        FieldType type = getFieldType(reader, dataDefinition, name, fieldTag, fieldType);
        fieldDefinition.withType(type);

        if (getBooleanAttribute(reader, "required", false)) {
            fieldDefinition.withValidator(getValidatorDefinition(reader, new RequiredValidator()));
        }

        if (getBooleanAttribute(reader, "unique", false)) {
            fieldDefinition.withValidator(getValidatorDefinition(reader, new UniqueValidator()));
        }

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagEnded(reader, fieldType)) {
                break;
            }

            String tag = getTagStarted(reader);

            if (tag == null) {
                continue;
            }

            addFieldElement(reader, fieldDefinition, type, tag);
        }

        return fieldDefinition;
    }

    private void addFieldElement(final XMLStreamReader reader, final FieldDefinitionImpl fieldDefinition, final FieldType type,
            final String tag) throws HookInitializationException, ModelXmlParsingException {
        switch (FieldTag.valueOf(tag.toUpperCase(Locale.ENGLISH))) {
            case VALIDATESLENGTH:
                fieldDefinition.withValidator(getValidatorDefinition(reader,
                        new LengthValidator(getIntegerAttribute(reader, "min"), getIntegerAttribute(reader, "is"),
                                getIntegerAttribute(reader, "max"))));
                break;
            case VALIDATESUNSCALEDVALUE:
                fieldDefinition.withValidator(getValidatorDefinition(reader,
                        new UnscaledValueValidator(getIntegerAttribute(reader, "min"), getIntegerAttribute(reader, "is"),
                                getIntegerAttribute(reader, "max"))));
                break;
            case VALIDATESSCALE:
                fieldDefinition.withValidator(getValidatorDefinition(reader,
                        new ScaleValidator(getIntegerAttribute(reader, "min"), getIntegerAttribute(reader, "is"),
                                getIntegerAttribute(reader, "max"))));
                break;
            case VALIDATESRANGE:
                Object from = getRangeForType(getStringAttribute(reader, "from"), type);
                Object to = getRangeForType(getStringAttribute(reader, "to"), type);
                boolean exclusively = getBooleanAttribute(reader, "exclusively", false);
                fieldDefinition.withValidator(getValidatorDefinition(reader, new RangeValidator(from, to, exclusively)));
                break;
            case VALIDATESWITH:
                fieldDefinition
                        .withValidator(getValidatorDefinition(reader, new CustomValidator(getFieldHookDefinition(reader))));
                break;
            case VALIDATESREGEX:
                fieldDefinition.withValidator(getValidatorDefinition(reader,
                        new RegexValidator(getStringAttribute(reader, "pattern"))));
                break;
            default:
                throw new ModelXmlParsingException("Illegal type of field's validator '" + tag + "'");
        }
    }

    private FieldType getFieldType(final XMLStreamReader reader, final DataDefinition dataDefinition, final String fieldName,
            final FieldsTag fieldTag, final String fieldType) throws XMLStreamException, ModelXmlParsingException {
        switch (fieldTag) {
            case INTEGER:
                return new IntegerType();
            case STRING:
                return new StringType();
            case FILE:
                return new FileType();
            case TEXT:
                return new TextType();
            case DECIMAL:
                return new DecimalType();
            case DATETIME:
                return new DateTimeType();
            case DATE:
                return new DateType();
            case BOOLEAN:
                return new BooleanType();
            case BELONGSTO:
                return getBelongsToType(reader, dataDefinition.getPluginIdentifier());
            case HASMANY:
                return getHasManyType(reader, dataDefinition.getPluginIdentifier());
            case MANYTOMANY:
                return getManyToManyType(reader, dataDefinition.getPluginIdentifier());
            case TREE:
                return getTreeType(reader, dataDefinition.getPluginIdentifier());
            case ENUM:
                return getEnumType(reader, dataDefinition.getPluginIdentifier() + "." + dataDefinition.getName() + "."
                        + fieldName);
            case DICTIONARY:
                return getDictionaryType(reader);
            case PASSWORD:
                return new PasswordType(passwordEncoder);
            default:
                throw new ModelXmlParsingException("Illegal type of field '" + fieldType + "'");
        }
    }

    private Object getRangeForType(final String range, final FieldType type) throws ModelXmlParsingException {
        if (range == null) {
            return null;
        } else if (type instanceof DateTimeType) {
            try {
                return new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT).parse(range);
            } catch (ParseException e) {
                throw new ModelXmlParsingException("Range '" + range + "' has invalid datetime format, should match "
                        + DateUtils.DATE_TIME_FORMAT, e);
            }
        } else if (type instanceof DateType) {
            try {
                return new SimpleDateFormat(DateUtils.DATE_FORMAT).parse(range);
            } catch (ParseException e) {
                throw new ModelXmlParsingException("Range '" + range + "' has invalid date format, should match "
                        + DateUtils.DATE_FORMAT, e);
            }
        } else if (type instanceof DecimalType) {
            return new BigDecimal(range);
        } else if (type instanceof IntegerType) {
            return Integer.parseInt(range);
        } else {
            return range;
        }
    }

    private FieldHookDefinition getValidatorDefinition(final XMLStreamReader reader, final FieldHookDefinition validator) {
        String customMessage = getStringAttribute(reader, "message");
        if (StringUtils.hasText(customMessage) && validator instanceof ErrorMessageDefinition) {
            ((ErrorMessageDefinition) validator).setErrorMessage(customMessage);
        }
        return validator;
    }

    private EntityHookDefinition getHookDefinition(final XMLStreamReader reader) throws HookInitializationException {
        String className = getStringAttribute(reader, "class");
        String methodName = getStringAttribute(reader, "method");
        return new EntityHookDefinitionImpl(className, methodName, applicationContext);
    }

    private FieldHookDefinition getFieldHookDefinition(final XMLStreamReader reader) throws HookInitializationException {
        String className = getStringAttribute(reader, "class");
        String methodName = getStringAttribute(reader, "method");
        return new FieldHookDefinitionImpl(className, methodName, applicationContext);
    }

    private FieldDefinition getPriorityFieldDefinition(final XMLStreamReader reader, final DataDefinitionImpl dataDefinition) {
        String scopeAttribute = getStringAttribute(reader, "scope");
        FieldDefinition scopedField = null;
        if (scopeAttribute != null) {
            scopedField = dataDefinition.getField(scopeAttribute);
        }
        return new FieldDefinitionImpl(dataDefinition, getStringAttribute(reader, "name"))
                .withType(new PriorityType(scopedField));
    }

}
