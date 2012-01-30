package com.qcadoo.view.internal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ContextualHelpPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private Map<String, String> urlsByComponentPathMap;

    public ContextualHelpPropertyConfigurer() {
        super();
        urlsByComponentPathMap = new HashMap<String, String>();
        setIgnoreResourceNotFound(true);
        setIgnoreUnresolvablePlaceholders(true);
        setLocation(new ClassPathResource("help.properties"));
    }

    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactory, final Properties props) {
        super.processProperties(beanFactory, props);
        for (Object key : props.keySet()) {
            String componentPath = key.toString();
            urlsByComponentPathMap.put(componentPath, resolvePlaceholder(componentPath, props));
        }
    }

    public String getProperty(final String code, final Locale locale) {
        return urlsByComponentPathMap.get(code + '_' + locale.getLanguage());
    }
}
