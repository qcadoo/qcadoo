package com.qcadoo.customTranslation.internal.aop;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.qcadoo.localization.internal.module.TranslationModule;

public class TranslationModuleOverrideAspectTest {

    @Test
    public final void checkEnableExecutionPointcutDefinition() throws NoSuchMethodException {
        Class<?> clazz = TranslationModule.class;
        assertEquals("com.qcadoo.localization.internal.module.TranslationModule", clazz.getCanonicalName());
        final Method method = clazz.getDeclaredMethod("enable");
        assertNotNull(method);
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public final void checkDisableExecutionPointcutDefinition() throws NoSuchMethodException {
        Class<?> clazz = TranslationModule.class;
        assertEquals("com.qcadoo.localization.internal.module.TranslationModule", clazz.getCanonicalName());
        final Method method = clazz.getDeclaredMethod("disable");
        assertNotNull(method);
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
    }

}
