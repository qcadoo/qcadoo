package com.qcadoo.customTranslation.internal.aop;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;

import org.junit.Test;

import com.qcadoo.localization.internal.TranslationServiceImpl;

public class TranslationServiceOverrideAspectTest {

    @Test
    public final void checkTranslateWithErrorExecutionPointcutDefinition() throws NoSuchMethodException {
        Class<?> clazz = TranslationServiceImpl.class;
        assertEquals("com.qcadoo.localization.internal.TranslationServiceImpl", clazz.getCanonicalName());
        final Method method = clazz.getDeclaredMethod("translateWithError", String.class, Locale.class, String[].class);
        assertNotNull(method);
        assertTrue(Modifier.isPrivate(method.getModifiers()));
        assertEquals(String.class, method.getReturnType());
    }

}
