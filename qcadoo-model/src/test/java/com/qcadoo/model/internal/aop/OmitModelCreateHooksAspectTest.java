package com.qcadoo.model.internal.aop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.DataAccessServiceImpl;
import com.qcadoo.model.internal.api.InternalDataDefinition;

public class OmitModelCreateHooksAspectTest {

    @Test
    public final void checkCopyEntityPointcutDefinition() throws NoSuchMethodException {
        final Class<?> clazz = DataAccessServiceImpl.class;
        assertEquals("com.qcadoo.model.internal.DataAccessServiceImpl", clazz.getCanonicalName());
        assertEquals("com.qcadoo.model.internal.api.InternalDataDefinition", InternalDataDefinition.class.getCanonicalName());
        final Method method = clazz.getMethod("copy", InternalDataDefinition.class, Long[].class);
        assertNotNull(method);
    }

    @Test
    public final void checkCallCreateHookPointcutDefinition() throws NoSuchMethodException {
        final Class<?> clazz = InternalDataDefinition.class;
        assertEquals("com.qcadoo.model.internal.api.InternalDataDefinition", clazz.getCanonicalName());
        assertEquals("com.qcadoo.model.api.Entity", Entity.class.getCanonicalName());
        final Method method = clazz.getMethod("callCreateHook", Entity.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
    }

}
