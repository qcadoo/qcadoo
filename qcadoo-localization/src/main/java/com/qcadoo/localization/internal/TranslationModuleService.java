package com.qcadoo.localization.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TranslationModuleService {

    private static final Logger LOG = LoggerFactory.getLogger(TranslationModuleService.class);

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ApplicationContext applicationContext;

    private Set<String> basenames = new LinkedHashSet<String>();

    @PostConstruct
    public void init() {
        basenames.add("WEB-INF/locales/messages");
    }

    public void addTranslationModule(final Collection<? extends String> moduleBasenames) {
        basenames.addAll(moduleBasenames);
        messageSource.clearCache();
        messageSource.setBasenames(basenames.toArray(new String[basenames.size()]));
    }

    public void removeTranslationModul(final Collection<? extends String> moduleBasenames) {
        basenames.removeAll(moduleBasenames);
        messageSource.clearCache();
        messageSource.setBasenames(basenames.toArray(new String[basenames.size()]));
    }

    public List<Resource> getLocalizationResources() {
        List<Resource> resources = new LinkedList<Resource>();
        for (String basename : basenames) {
            String searchName = basename + "*.properties";
            try {
                resources.addAll(Arrays.asList(applicationContext.getResources(searchName)));
            } catch (IOException e) {
                LOG.error("Cannot read messages file", e);
            }
        }
        return resources;
    }
}
