/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;

import javax.xml.stream.FactoryConfigurationError;
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

    private static final Logger LOG = LoggerFactory.getLogger(ModelXmlToClassConverterImpl.class);

    private final ClassPool classPool = ClassPool.getDefault();

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Collection<Class<?>> convert(final Resource... resources) {
        try {
            Map<String, CtClass> ctClasses = new HashMap<String, CtClass>();
            Map<String, Class<?>> existingClasses = new HashMap<String, Class<?>>();

            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    LOG.info("Getting existing classes from " + resource);
                    existingClasses.putAll(findExistingClasses(resource.getInputStream()));
                }
            }

            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    LOG.info("Creating classes from " + resource);
                    ctClasses.putAll(createClasses(existingClasses, resource.getInputStream()));
                }
            }

            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    LOG.info("Defining classes from " + resource + " to classes");
                    defineClasses(ctClasses, resource.getInputStream());
                }
            }

            List<Class<?>> classes = new ArrayList<Class<?>>();

            for (CtClass ctClass : ctClasses.values()) {
                classes.add(ctClass.toClass(classLoader));
            }

            classes.addAll(existingClasses.values());

            return classes;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to convert model.xml to classes", e);
        } catch (CannotCompileException e) {
            throw new IllegalStateException("Failed to convert model.xml to classes", e);
        } catch (XMLStreamException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (FactoryConfigurationError e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private Map<String, Class<?>> findExistingClasses(final InputStream stream) throws XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
        Map<String, Class<?>> existingClasses = new HashMap<String, Class<?>>();

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                String pluginIdentifier = getPluginIdentifier(reader);
                String modelName = getStringAttribute(reader, "name");
                String className = ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName);

                try {
                    existingClasses.put(className, classLoader.loadClass(className));
                    LOG.info("Class " + className + " already exists, skipping");
                } catch (ClassNotFoundException e) {
                    // ignoring
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
                String modelName = getStringAttribute(reader, "name");
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

    private void defineClasses(final Map<String, CtClass> ctClasses, final InputStream stream) throws XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagStarted(reader, TAG_MODEL)) {
                String pluginIdentifier = getPluginIdentifier(reader);
                String modelName = getStringAttribute(reader, "name");
                String className = ClassNameUtils.getFullyQualifiedClassName(pluginIdentifier, modelName);

                if (ctClasses.containsKey(className)) {
                    parse(reader, ctClasses.get(className), pluginIdentifier);
                }
            }
        }

        reader.close();
    }

    private void parse(final XMLStreamReader reader, final CtClass ctClass, final String pluginIdentifier)
            throws XMLStreamException {
        LOG.info("Defining class " + ctClass.getName());

        createField(ctClass, "id", Long.class.getCanonicalName());

        List<String> fields = new ArrayList<String>();
        fields.add("id");

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

    private void buildToString(final CtClass ctClass, final List<String> fields) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("return new java.lang.StringBuilder().append(\"" + ctClass.getName() + "[\").");

            boolean first = true;

            for (String field : fields) {
                if (!first) {
                    sb.append("append(\",\").");
                } else {
                    first = false;
                }
                sb.append("append(\"" + field + "=\").append(get" + StringUtils.capitalize(field) + "()).");
            }

            sb.append("append(\"]\").toString();");

            ctClass.addMethod(CtNewMethod.make("public String toString() { " + sb.toString() + " }", ctClass));
        } catch (CannotCompileException e) {
            throw new IllegalStateException("Failed to compile class " + ctClass.getName(), e);
        }
    }

    private void buildHashCode(final CtClass ctClass, final List<String> fields) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("final int prime = 31;");
            sb.append("int result = 1;");

            for (String field : fields) {
                sb.append("result = prime * result + ((get" + StringUtils.capitalize(field) + "() == null) ? 0 : get"
                        + StringUtils.capitalize(field) + "().hashCode());");
            }

            sb.append("return result;");

            ctClass.addMethod(CtNewMethod.make("public int hashCode() { " + sb.toString() + " }", ctClass));
        } catch (CannotCompileException e) {
            throw new IllegalStateException("Failed to compile class " + ctClass.getName(), e);
        }
    }

    private void buildEquals(final CtClass ctClass, final List<String> fields) {
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
            throw new IllegalStateException("Failed to compile class " + ctClass.getName(), e);
        }
    }

    private void parseField(final XMLStreamReader reader, final String pluginIdentifier, final CtClass ctClass, final String tag,
            final List<String> fields) throws XMLStreamException {
        FieldsTag modelTag = FieldsTag.valueOf(tag.toUpperCase(Locale.ENGLISH));

        if (!getBooleanAttribute(reader, "persistent", true)) {
            return;
        }

        if (getStringAttribute(reader, "expression") != null) {
            return;
        }

        switch (modelTag) {
            case PRIORITY:
            case INTEGER:
                createField(ctClass, getStringAttribute(reader, "name"), Integer.class.getCanonicalName());
                fields.add(getStringAttribute(reader, "name"));
                break;
            case STRING:
            case TEXT:
            case ENUM:
            case DICTIONARY:
            case PASSWORD:
                createField(ctClass, getStringAttribute(reader, "name"), String.class.getCanonicalName());
                fields.add(getStringAttribute(reader, "name"));
                break;
            case DECIMAL:
                createField(ctClass, getStringAttribute(reader, "name"), BigDecimal.class.getCanonicalName());
                fields.add(getStringAttribute(reader, "name"));
                break;
            case DATETIME:
            case DATE:
                createField(ctClass, getStringAttribute(reader, "name"), Date.class.getCanonicalName());
                fields.add(getStringAttribute(reader, "name"));
                break;
            case BOOLEAN:
                createField(ctClass, getStringAttribute(reader, "name"), Boolean.class.getCanonicalName());
                fields.add(getStringAttribute(reader, "name"));
                break;
            case BELONGSTO:
                createBelongsField(ctClass, pluginIdentifier, reader);
                fields.add(getStringAttribute(reader, "name"));
                break;
            case HASMANY:
            case TREE:
                createHasManyField(ctClass, reader);
                break;
            default:
                break;
        }

        while (reader.hasNext() && reader.next() > 0) {
            if (isTagEnded(reader, tag)) {
                break;
            }
        }
    }

    private void createHasManyField(final CtClass ctClass, final XMLStreamReader reader) {
        createField(ctClass, getStringAttribute(reader, "name"), "java.util.Set");
    }

    private void createBelongsField(final CtClass ctClass, final String pluginIdentifier, final XMLStreamReader reader) {
        String plugin = getStringAttribute(reader, "plugin");

        if (plugin == null) {
            plugin = pluginIdentifier;
        }

        String model = getStringAttribute(reader, "model");

        createField(ctClass, getStringAttribute(reader, "name"), ClassNameUtils.getFullyQualifiedClassName(plugin, model));
    }

    private void createField(final CtClass ctClass, final String name, final String clazz) {
        try {
            ctClass.addField(CtField.make("private " + clazz + " " + name + ";", ctClass));
            ctClass.addMethod(CtNewMethod.make("public " + clazz + " get" + StringUtils.capitalize(name) + "() { return " + name
                    + "; }", ctClass));
            ctClass.addMethod(CtNewMethod.make("public void set" + StringUtils.capitalize(name) + "(" + clazz + " " + name
                    + ") { this." + name + " = " + name + "; }", ctClass));
        } catch (CannotCompileException e) {
            throw new IllegalStateException("Failed to compile class " + ctClass.getName(), e);
        }
    }

}
