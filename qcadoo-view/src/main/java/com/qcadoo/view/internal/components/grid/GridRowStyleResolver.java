package com.qcadoo.view.internal.components.grid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Node;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

/**
 * CSS classes resolver for grid rows.
 * 
 * @author marcinkubala
 * @since 1.2.0
 */
public final class GridRowStyleResolver {

    private final Object bean;

    private final Method method;

    /**
     * @param styleResoverNode
     *            <rowStyleResolver /> node
     * @param parser
     *            view definition parser
     * @param applicationContext
     *            spring container's application context
     * @return new instance of {@link GridRowStyleResolver} based on class and method fetched from view XML.
     */
    public static GridRowStyleResolver build(final Node styleResoverNode, final ViewDefinitionParser parser,
            final ApplicationContext applicationContext) {
        final String resolverClass = parser.getStringAttribute(styleResoverNode, "class");
        final String resolverMethod = parser.getStringAttribute(styleResoverNode, "method");
        return new GridRowStyleResolver(resolverClass, resolverMethod, applicationContext);
    }

    /**
     * @param className
     *            binary name (http://docs.oracle.com/javase/6/docs/api/java/lang/ClassLoader.html#name) of class containing
     *            resolver method
     * @param methodName
     *            resolver method name
     * @param applicationContext
     */
    public GridRowStyleResolver(final String className, final String methodName, final ApplicationContext applicationContext) {
        Preconditions.checkArgument(!StringUtils.isBlank(className), "class name is not specified!");
        Preconditions.checkArgument(!StringUtils.isBlank(methodName), "method name is not specified!");

        final Class<?> clazz = getResolverClass(className);

        bean = getResolverBean(clazz, applicationContext);
        method = getMethod(clazz, methodName);

        checkResolverMethodSignature();
    }

    /**
     * Resolve CSS classes for grid rows
     * 
     * @param entities
     *            list of entities which the CSS class will be resolved
     * @return entity identifiers mapped to appropriate set of the CSS classes.
     */
    public Map<Long, Set<String>> resolve(final List<Entity> entities) {
        final Map<Long, Set<String>> entityIdsToStylesMap = Maps.newHashMap();
        for (Entity entity : entities) {
            if (entity.getId() == null) {
                continue;
            }
            final Set<String> cssClassesForEntity = invokeResolve(entity);
            if (!cssClassesForEntity.isEmpty()) {
                entityIdsToStylesMap.put(entity.getId(), cssClassesForEntity);
            }
        }
        return entityIdsToStylesMap;
    }

    @SuppressWarnings("unchecked")
    private Set<String> invokeResolve(final Entity entity) {
        try {
            final Object resolverInvocationResults = method.invoke(bean, entity);
            if (resolverInvocationResults == null) {
                return Collections.EMPTY_SET;
            } else {
                return (Set<String>) resolverInvocationResults;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to invoke grid style resolver method", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to invoke grid style resolver method", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Failed to invoke grid style resolver method", e);
        }
    }

    private Class<?> getResolverClass(final String className) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to find class '" + className + "', please make sure that there is no typo", e);
        }
    }

    private Object getResolverBean(final Class<?> clazz, final ApplicationContext applicationContext) {
        Object hookBean = applicationContext.getBean(clazz);
        if (hookBean == null) {
            throw new IllegalStateException(
                    "Failed to find bean for class '"
                            + clazz.getCanonicalName()
                            + "', please make sure that there is no typo, class have @Service or @Component annotation and its package is registered in the Spring component-scan feature");
        }
        return hookBean;
    }

    private void checkResolverMethodSignature() {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalStateException("Grid style resolver method '" + method.getDeclaringClass().getCanonicalName() + "#"
                    + method.getName() + "' has invalid visibility, must be public");
        }
        if (!getReturnType().equals(method.getReturnType())) {
            throw new IllegalStateException("Grid style resolver method '" + method.getDeclaringClass().getCanonicalName() + "#"
                    + method.getName() + "' has invalid return type, must be String[]");
        }
    }

    private Method getMethod(final Class<?> clazz, final String methodName) {
        try {
            return clazz.getMethod(methodName, getParameterTypes());
        } catch (SecurityException e) {
            throw new IllegalStateException("Failed to access grid style resolver method '" + clazz.getCanonicalName() + "#"
                    + methodName + "'", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Failed to find grid style resolver method '" + clazz.getCanonicalName() + "#"
                    + methodName + "', please make sure that there is no typo, method returns " + getReturnType()
                    + " and parameters' types are valid (" + Arrays.toString(getParameterTypes()) + ")", e);
        }
    }

    private Class<?> getReturnType() {
        return Set.class;
    }

    private Class<?>[] getParameterTypes() {
        return new Class[] { Entity.class };
    }

}
