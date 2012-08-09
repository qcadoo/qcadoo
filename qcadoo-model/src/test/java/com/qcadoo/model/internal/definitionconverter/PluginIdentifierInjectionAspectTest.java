package com.qcadoo.model.internal.definitionconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.AbstractModelXmlConverter;
import com.qcadoo.model.internal.AbstractModelXmlConverter.FieldsTag;
import com.qcadoo.model.internal.DataDefinitionImpl;
import com.qcadoo.model.internal.api.EntityHookDefinition;
import com.qcadoo.model.internal.api.FieldHookDefinition;
import com.qcadoo.model.internal.definitionconverter.ModelXmlToDefinitionConverterImpl.PluginIdentifierInjectionAspect;

public class PluginIdentifierInjectionAspectTest {

    @Test
    public final void checkExecGetDataDefinitionPointcutDefinition() throws NoSuchMethodException {
        assertEquals(
                "com.qcadoo.model.internal.definitionconverter.ModelXmlToDefinitionConverterImpl.PluginIdentifierInjectionAspect",
                PluginIdentifierInjectionAspect.class.getCanonicalName());

        final Class<?> clazz = ModelXmlToDefinitionConverterImpl.class;
        final Method method = clazz.getDeclaredMethod("getDataDefinition", XMLStreamReader.class, String.class);
        assertEquals("javax.xml.stream.XMLStreamReader", XMLStreamReader.class.getCanonicalName());
        assertNotNull(method);
    }

    @Test
    public final void checkExecHookDefinitionGetterPointcutDefinition() throws NoSuchMethodException {
        assertEquals(
                "com.qcadoo.model.internal.definitionconverter.ModelXmlToDefinitionConverterImpl.PluginIdentifierInjectionAspect",
                PluginIdentifierInjectionAspect.class.getCanonicalName());

        final Class<?> clazz = ModelXmlToDefinitionConverterImpl.class;

        final Method method1 = findMethod(clazz, EntityHookDefinition.class, XMLStreamReader.class, String.class);
        final Method method2 = findMethod(clazz, FieldHookDefinition.class, XMLStreamReader.class, String.class);
        assertTrue(method1 != null || method2 != null);
        assertEquals("javax.xml.stream.XMLStreamReader", XMLStreamReader.class.getCanonicalName());

        assertEquals("com.qcadoo.model.internal.api.EntityHookDefinition", EntityHookDefinition.class.getCanonicalName());
        assertEquals("com.qcadoo.model.internal.api.FieldHookDefinition", FieldHookDefinition.class.getCanonicalName());
    }

    @Test
    public final void checkExecFieldDefinitionGetterPointcutDefinition() throws NoSuchMethodException {
        assertEquals(
                "com.qcadoo.model.internal.definitionconverter.ModelXmlToDefinitionConverterImpl.PluginIdentifierInjectionAspect",
                PluginIdentifierInjectionAspect.class.getCanonicalName());

        final Class<?> clazz = ModelXmlToDefinitionConverterImpl.class;

        final Method method = clazz.getDeclaredMethod("getFieldDefinition", XMLStreamReader.class, DataDefinitionImpl.class,
                AbstractModelXmlConverter.FieldsTag.class);
        assertEquals("javax.xml.stream.XMLStreamReader", XMLStreamReader.class.getCanonicalName());
        assertEquals("com.qcadoo.model.internal.DataDefinitionImpl", DataDefinitionImpl.class.getCanonicalName());
        assertEquals("com.qcadoo.model.internal.AbstractModelXmlConverter.FieldsTag", FieldsTag.class.getCanonicalName());

        assertNotNull(method);
        assertEquals(FieldDefinition.class, method.getReturnType());
        assertEquals("com.qcadoo.model.api.FieldDefinition", FieldDefinition.class.getCanonicalName());

    }

    private Method findMethod(final Class<?> clazz, final Class<?> retType, final Class<?>... args) {
        final List<Method> methods = Lists.newArrayList(clazz.getMethods());
        methods.addAll(Lists.newArrayList(clazz.getDeclaredMethods()));
        for (Method method : methods) {
            if (methodMatchArguments(method, args) && retType.isAssignableFrom(method.getReturnType())) {
                return method;
            }
        }
        Assert.fail("method not found");
        return null;
    }

    private boolean methodMatchArguments(final Method method, final Class<?>... args) {
        final Class<?>[] methodArguments = method.getParameterTypes();
        if ((methodArguments == null && args != null) || (methodArguments != null && args == null)
                || methodArguments.length != args.length) {
            return false;
        }
        int index = 0;
        for (Class<?> argClazz : args) {
            if (!argClazz.isAssignableFrom(methodArguments[index])) {
                return false;
            }
            index++;
        }
        return true;
    }
}
