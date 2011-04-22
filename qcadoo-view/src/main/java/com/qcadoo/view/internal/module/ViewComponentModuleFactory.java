package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.api.ComponentPattern;
import com.qcadoo.view.internal.internal.ViewComponentsResolverImpl;

public class ViewComponentModuleFactory extends ModuleFactory<Module> implements BeanClassLoaderAware {

    @Autowired
    private ViewComponentsResolverImpl viewComponentsResolver;

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Module parse(final String pluginIdentifier, final Element element) {
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

        return new Module();
    }

    @Override
    public String getIdentifier() {
        return "view-component";
    }

}
