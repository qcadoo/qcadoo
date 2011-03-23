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

package com.qcadoo.model.internal.hooks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.context.ApplicationContext;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.FieldDefinition;

public abstract class HookDefinitionImpl {

    private final Object bean;

    private final Method method;

    private DataDefinition dataDefinition;

    private FieldDefinition fieldDefinition;

    public HookDefinitionImpl(final String className, final String methodName, final ApplicationContext applicationContext) {
        Class<?> clazz = getHookClass(className);

        bean = getHookBean(clazz, applicationContext);
        method = getMethod(clazz, methodName);

        checkHookMethodModifiers();
    }

    public abstract Class<?>[] getParameterTypes();

    public final void initialize(final DataDefinition dataDefinition) {
        this.dataDefinition = dataDefinition;
    }

    public final void initialize(final DataDefinition dataDefinition, final FieldDefinition fieldDefinition) {
        this.dataDefinition = dataDefinition;
        this.fieldDefinition = fieldDefinition;
    }

    protected final boolean call(final Object... args) {
        try {
            Object result = method.invoke(bean, args);

            if (result instanceof Boolean) {
                return (Boolean) result;
            } else {
                return true;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to invoke hook method", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to invoke hook method", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Failed to invoke hook method", e);
        }
    }

    public final Method getMethod() {
        return method;
    }

    public final Object getBean() {
        return bean;
    }

    private Class<?> getHookClass(final String hookClassName) {
        try {
            return HookDefinitionImpl.class.getClassLoader().loadClass(hookClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to find hook class", e);
        }
    }

    private Object getHookBean(final Class<?> clazz, final ApplicationContext applicationContext) {
        Object hookBean = applicationContext.getBean(clazz);
        if (hookBean == null) {
            throw new IllegalStateException("Failed to find bean for class " + clazz.getCanonicalName());
        }
        return hookBean;
    }

    private void checkHookMethodModifiers() {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalStateException("Hook method " + method.getDeclaringClass().getCanonicalName() + "."
                    + method.getName() + " must be public");
        }
    }

    private Method getMethod(final Class<?> clazz, final String methodName) {
        try {
            return clazz.getMethod(methodName, getParameterTypes());
        } catch (SecurityException e) {
            throw new IllegalStateException("Failed to find hook method " + clazz.getCanonicalName() + "." + methodName, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Failed to find hook method " + clazz.getCanonicalName() + "." + methodName, e);
        }
    }

    public DataDefinition getDataDefinition() {
        return dataDefinition;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

}
