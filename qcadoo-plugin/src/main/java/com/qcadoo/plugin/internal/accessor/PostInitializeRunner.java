package com.qcadoo.plugin.internal.accessor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

@Service
public class PostInitializeRunner implements ApplicationListener<ContextStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(PostInitializeRunner.class);

    @Override
    public void onApplicationEvent(final ContextStartedEvent contextStartedEvent) {
        LOG.info("Scanning for Post Initializers...");

        ApplicationContext applicationContext = contextStartedEvent.getApplicationContext();
        Set<PostInitializingMethod> postInitializingMethods = getPostInitializingMethods(applicationContext);

        LOG.info("Application Context scan completed, " + postInitializingMethods.size()
                + " post initializers found. Invoking now.");

        invokePostInitializeMethods(postInitializingMethods);

    }

    private Set<PostInitializingMethod> getPostInitializingMethods(final ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansOfType(Object.class, false, true);
        Set<PostInitializingMethod> postInitializingMethods = Sets.newTreeSet();
        for (Object beanNameObject : beans.keySet()) {
            String beanName = (String) beanNameObject;
            Object bean = beans.get(beanNameObject);
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                PostInitialize methodAnnotation = getPostInitializeAnnotation(method);
                if (methodAnnotation == null) {
                    continue;
                }
                if (method.getParameterTypes().length == 0) {
                    int order = methodAnnotation.order();
                    postInitializingMethods.add(new PostInitializingMethod(method, bean, order, beanName));
                } else {
                    LOG.warn("Post Initializer method can't have any arguments. " + method.toGenericString() + " in bean "
                            + beanName + " won't be invoked");
                }
            }
        }
        return postInitializingMethods;
    }

    private void invokePostInitializeMethods(final Set<PostInitializingMethod> postInitializingMethods) {
        for (PostInitializingMethod postInitializingMethod : postInitializingMethods) {
            Method method = postInitializingMethod.getMethod();
            Object target = postInitializingMethod.getBeanInstance();
            try {
                method.invoke(target);
            } catch (Throwable e) {
                throw new BeanCreationException("Post Initialization of bean " + postInitializingMethod.getBeanName()
                        + " failed.", e.getCause());
            }
        }
    }

    private PostInitialize getPostInitializeAnnotation(final Method method) {
        if (method != null && method.isAnnotationPresent(PostInitialize.class)) {
            return method.getAnnotation(PostInitialize.class);
        }
        return null;
    }

    private class PostInitializingMethod implements Comparable<PostInitializingMethod> {

        private Method method;

        private Object beanInstance;

        private int order;

        private String beanName;

        private PostInitializingMethod(Method method, Object beanInstance, int order, String beanName) {
            this.method = method;
            this.beanInstance = beanInstance;
            this.order = order;
            this.beanName = beanName;
        }

        public Method getMethod() {
            return method;
        }

        public Object getBeanInstance() {
            return beanInstance;
        }

        public String getBeanName() {
            return beanName;
        }

        public int getOrder() {
            return order;
        }

        @Override
        public int compareTo(final PostInitializingMethod other) {
            return new CompareToBuilder().append(getOrder(), other.getOrder()).toComparison();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PostInitializingMethod other = (PostInitializingMethod) o;
            return new EqualsBuilder().append(getOrder(), other.getOrder()).append(getBeanName(), other.getBeanName())
                    .append(getMethod(), other.getMethod()).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(1, 31).append(method).append(beanInstance).append(beanName).toHashCode();
        }
    }

}
