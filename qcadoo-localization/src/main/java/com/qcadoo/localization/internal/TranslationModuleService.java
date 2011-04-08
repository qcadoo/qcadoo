package com.qcadoo.localization.internal;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class TranslationModuleService {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

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

}
