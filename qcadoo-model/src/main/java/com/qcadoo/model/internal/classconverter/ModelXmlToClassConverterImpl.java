/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.6
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
package com.qcadoo.model.internal.classconverter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.qcadoo.model.internal.AbstractModelXmlConverter;
import com.qcadoo.model.internal.api.ModelXmlToClassConverter;
import com.qcadoo.model.internal.utils.ClassNameUtils;

@Component
public final class ModelXmlToClassConverterImpl extends AbstractModelXmlConverter implements ModelXmlToClassConverter,
        BeanClassLoaderAware {

    private static final String L_FAILED_TO_COMPILE_CLASS = "Failed to compile class ";

    private static final String L_NAME = "name";

    private static final String L_ERROR_WHILE_PARSING_MODEL_XML = "Error while parsing model.xml: ";

    private static final Logger LOG = LoggerFactory.getLogger(ModelXmlToClassConverterImpl.class);

    private final ClassPool classPool = ClassPool.getDefault();

    private ClassLoader classLoader;

    public ModelXmlToClassConverterImpl() {
        super();
        classPool.appendClassPath(new ClassClassPath(org.hibernate.collection.PersistentSet.class));
    }

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Collection<Class<?>> convert(final Resource... resources) {
        Map<String, CtClass> ctClasses = new HashMap<String, CtClass>();
        Map<String, Class<?>> existingClasses = new HashMap<String, Class<?>>();

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                LOG.info("Getting existing classes from " + resource);
                try {
                    existingClasses.putAll(findExistingClasses(resource.getInputStream()));
                } catch (XMLStreamException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                } catch (IOException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                }
            }
        }

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                LOG.info("Creating classes from " + resource);
                try {
                    ctClasses.putAll(createClasses(existingClasses, resource.getInputStream()));
                } catch (XMLStreamException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                } catch (IOException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                }
            }
        }

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                LOG.info("Defining classes from " + resource + " to classes");
                try {
                    defineClasses(ctClasses, resource.getInputStream());
                } catch (XMLStreamException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                } catch (ModelXmlCompilingException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                } catch (IOException e) {
                    throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
                }
            }
        }

        List<Class<?>> classes = new ArrayList<Class<?>>();

        for (CtClass ctClass : ctClasses.values()) {
            try {
                classes.add(ctClass.toClass(classLoader));
            } catch (CannotCompileException e) {
                throw new IllegalStateException(L_ERROR_WHILE_PARSING_MODEL_XML + e.getMessage(), e);
            }
        }

        classes.addAll(existingClasses.values());

        return classes;
    }

    private Map<String, Class<?>> findExistingClasses(final InputStream stream) throws XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
        Map<String, Class<?>> existingClasses = new HashMap<String, Class<?>>();

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                String pluginIdentifier = getPluginIdentifier(reader);
                String modelName = getStringAttribute(reader, L_NAME);
                String className = ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName);

                try {
                    existingClasses.put(className, classLoader.loadClass(className));
                    LOG.info("Class " + className + " already exists, skipping");
                } catch (ClassNotFoundException e) {
                    LOG.info("Class " + className + " not found, will be generated");
                }

                break;
            }
        }

        reader.close();

        return existingClasses;
    }

    private Map<String, CtClass> createClasses(final Map<String, Class<?>> existingClasses, final InputStream stream)
            throws XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
        Map<String, CtClass> ctClasses = new HashMap<String, CtClass>();

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                String pluginIdentifier = getPluginIdentifier(reader);
                String modelName = getStringAttribute(reader, L_NAME);
                String className = ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName);

                if (existingClasses.containsKey(className)) {
                    LOG.info("Class " + className + " already exists, skipping");
                } else {
                    LOG.info("Creating class " + className);
                    ctClasses.put(className, classPool.makeClass(className));
                }

                break;
            }
        }

        reader.close();

        return ctClasses;
    }

    private void defineClasses(final Map<String, CtClass> ctClasses, final InputStream stream) throws XMLStreamException,
            ModelXmlCompilingException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                String pluginIdentifier = getPluginIdentifier(reader);
                String modelName = getStringAttribute(reader, L_NAME);
                String className = ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName);

                if (ctClasses.containsKey(className)) {
                    parse(reader, ctClasses.get(className), pluginIdentifier);
                }
            }
        }

        reader.close();
    }

    private void parse(final XMLStreamReader reader, final CtClass ctClass, final String pluginIdentifier)
            throws XMLStreamException, ModelXmlCompilingException {
        LOG.info("Defining class " + ctClass.getName());

        createField(ctClass, "id", Long.class.getCanonicalName());

        List<String> fields = new ArrayList<String>();
        fields.add("id");

        if (getBooleanAttribute(reader, "activable", false)) {
            createField(ctClass, "active", Boolean.class.getCanonicalName());
        }
        if (getBooleanAttribute(reader, "auditable", false)) {
            createField(ctClass, "createDate", Date.class.getCanonicalName());
            createField(ctClass, "updateDate", Date.class.getCanonicalName());
            createField(ctClass, "createUser", String.class.getCanonicalName());
            createField(ctClass, "updateUser", String.class.getCanonicalName());
        }

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

                    parseField(reader, pluginIdentifier, ctClass, tag, fields);
                }
                break;
            }

        }

        buildToString(ctClass, fields);
        buildHashCode(ctClass, fields);
        buildEquals(ctClass, fields);
    }

    private void buildToString(final CtClass ctClass, final List<String> fields) throws ModelXmlCompilingException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("return new java.lang.StringBuilder().append(\"" + ctClass.getName() + "[\").");

            boolean first = true;

            for (String field : fields) {
                if (first) {
                    first = false;
                } else {
                    sb.append("append(\",\").");
                }
                sb.append("append(\"" + field + "=\").append(get" + StringUtils.capitalize(field) + "()).");
            }

            sb.append("append(\"]\").toString();");

            ctClass.addMethod(CtNewMethod.make("public String toString() { " + sb.toString() + " }", ctClass));
        } catch (CannotCompileException e) {
            throw new ModelXmlCompilingException(L_FAILED_TO_COMPILE_CLASS + ctClass.getName(), e);
        }
    }

    private void buildHashCode(final CtClass ctClass, final List<String> fields) throws ModelXmlCompilingException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("final int prime = 31;");
            sb.append("int result = 1;");

            for (String field : fields) {
                String fieldGetter = "get" + StringUtils.capitalize(field) + "()";
                // explanation of the second condition -> https://hibernate.onjira.com/browse/HHH-3799
                sb.append("result = prime * result + ((" + fieldGetter + " == null || " + fieldGetter
                        + ".getClass().isAssignableFrom(org.hibernate.collection.PersistentSet.class)) ? 0 : " + fieldGetter
                        + ".hashCode());");
            }

            sb.append("return result;");

            ctClass.addMethod(CtNewMethod.make("public int hashCode() { " + sb.toString() + " }", ctClass));
        } catch (CannotCompileException e) {
            throw new ModelXmlCompilingException(L_FAILED_TO_COMPILE_CLASS + ctClass.getName(), e);
        }
    }

    private void buildEquals(final CtClass ctClass, final List<String> fields) throws ModelXmlCompilingException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("if (obj == null) { return false; }");
            sb.append("if (obj == this) { return true; }");
            sb.append("if (!(obj instanceof " + ctClass.getName() + ")) { return false; }");
            sb.append(ctClass.getName() + " other = (" + ctClass.getName() + ") obj;");

            for (String field : fields) {
                sb.append("if (get" + StringUtils.capitalize(field) + "() == null) {");
                sb.append("if (other.get" + StringUtils.capitalize(field) + "() != null) { return false; }");
                sb.append("} else if (!get" + StringUtils.capitalize(field) + "().equals(other.get"
                        + StringUtils.capitalize(field) + "())) { return false; }");
            }

            sb.append("return true;");

            ctClass.addMethod(CtNewMethod.make("public boolean equals(Object obj) { " + sb.toString() + " }", ctClass));
        } catch (CannotCompileException e) {
            throw new ModelXmlCompilingException(L_FAILED_TO_COMPILE_CLASS + ctClass.getName(), e);
        }
    }

    private void parseField(final XMLStreamReader reader, final String pluginIdentifier, final CtClass ctClass, final String tag,
            final List<String> fields) throws XMLStreamException, ModelXmlCompilingException {
        FieldsTag modelTag = FieldsTag.valueOf(tag.toUpperCase(Locale.ENGLISH));

        if (getBooleanAttribute(reader, "persistent", true) || getStringAttribute(reader, "expression") == null) {
            switch (modelTag) {
                case PRIORITY:
                case INTEGER:
                    createField(ctClass, getStringAttribute(reader, L_NAME), Integer.class.getCanonicalName());
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case STRING:
                case FILE:
                case TEXT:
                case ENUM:
                case DICTIONARY:
                case PASSWORD:
                    createField(ctClass, getStringAttribute(reader, L_NAME), String.class.getCanonicalName());
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case DECIMAL:
                    createField(ctClass, getStringAttribute(reader, L_NAME), BigDecimal.class.getCanonicalName());
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case DATETIME:
                case DATE:
                    createField(ctClass, getStringAttribute(reader, L_NAME), Date.class.getCanonicalName());
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case BOOLEAN:
                    createField(ctClass, getStringAttribute(reader, L_NAME), Boolean.class.getCanonicalName());
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case BELONGSTO:
                    createBelongsField(ctClass, pluginIdentifier, reader);
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case MANYTOMANY:
                    createSetField(ctClass, reader);
                    fields.add(getStringAttribute(reader, L_NAME));
                    break;
                case HASMANY:
                case TREE:
                    createSetField(ctClass, reader);
                    break;
                default:
                    break;
            }
        }

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagEnded(reader, tag)) {
                break;
            }
        }
    }

    private void createSetField(final CtClass ctClass, final XMLStreamReader reader) throws ModelXmlCompilingException {
        createField(ctClass, getStringAttribute(reader, L_NAME), "java.util.Set");
    }

    private void createBelongsField(final CtClass ctClass, final String pluginIdentifier, final XMLStreamReader reader)
            throws ModelXmlCompilingException {
        String plugin = getStringAttribute(reader, "plugin");

        if (plugin == null) {
            plugin = pluginIdentifier;
        }

        String model = getStringAttribute(reader, "model");

        createField(ctClass, getStringAttribute(reader, L_NAME), ClassNameUtils.getFullyQualifiedClassName(plugin, model));
    }

    private void createField(final CtClass ctClass, final String name, final String clazz) throws ModelXmlCompilingException {
        try {
            ctClass.addField(CtField.make("private " + clazz + " " + name + ";", ctClass));
            ctClass.addMethod(CtNewMethod.make("public " + clazz + " get" + StringUtils.capitalize(name) + "() { return " + name
                    + "; }", ctClass));
            ctClass.addMethod(CtNewMethod.make("public void set" + StringUtils.capitalize(name) + "(" + clazz + " " + name
                    + ") { this." + name + " = " + name + "; }", ctClass));
        } catch (CannotCompileException e) {
            throw new ModelXmlCompilingException(L_FAILED_TO_COMPILE_CLASS + ctClass.getName(), e);
        }
    }

}
