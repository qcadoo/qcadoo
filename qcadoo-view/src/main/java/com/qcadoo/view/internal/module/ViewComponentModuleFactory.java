package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.EmptyModule;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.api.ComponentPattern;
import com.qcadoo.view.internal.internal.ViewComponentsResolverImpl;

public class ViewComponentModuleFactory implements ModuleFactory<EmptyModule>, BeanClassLoaderAware {

    @Autowired
    private ViewComponentsResolverImpl viewComponentsResolver;

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void init() {
        // empty
    }

    @Override
    @SuppressWarnings("unchecked")
    public EmptyModule parse(final String pluginIdentifier, final Element element) {
        String name = element.getAttributeValue("name");
        String clazzName = element.getAttributeValue("class");

        if (name == null) {
            throw new IllegalStateException("Missing name attribute of component module");
        }

        if (clazzName == null) {
            throw new IllegalStateException("Missing class attribute of component module");
        }

        try {
            viewComponentsResolver.register(name, (Class<? extends ComponentPattern>) classLoader.loadClass(clazzName));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find component class: " + clazzName, e);
        }

        return new EmptyModule();
    }

    @Override
    public String getIdentifier() {
        return "component";
    }

}
