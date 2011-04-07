package com.qcadoo.localization.internal.module;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.qcadoo.localization.internal.TranslationModuleService;
import com.qcadoo.plugin.api.Module;

public class TranslationModule extends Module {

    private final ApplicationContext applicationContext;

    private final TranslationModuleService translationModuleService;

    private final Set<String> basenames = new LinkedHashSet<String>();

    private final String pluginIdentifier;

    private final String basename;

    private final String path;

    public TranslationModule(final ApplicationContext applicationContext,
            final TranslationModuleService translationModuleService, final String pluginIdentifier, final String basename,
            final String path) {
        this.applicationContext = applicationContext;
        this.translationModuleService = translationModuleService;
        this.pluginIdentifier = pluginIdentifier;
        this.basename = basename;
        this.path = path;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        if (basename == null) {
            basenames.addAll(getAllFilesFromPath());
        } else {
            basenames.add("classpath:" + pluginIdentifier + "/" + path + "/" + basename);
        }

        translationModuleService.addTranslationModule(basenames);
    }

    @Override
    public void disable() {
        if (basename == null) {
            basenames.removeAll(getAllFilesFromPath());
        } else {
            basenames.remove("classpath:" + pluginIdentifier + "/" + path + "/" + basename);
        }

        translationModuleService.removeTranslationModul(basenames);
    }

    private Collection<? extends String> getAllFilesFromPath() {

        Set<String> basenamesInDirectory = new LinkedHashSet<String>();

        try {
            Resource[] resources = applicationContext.getResources("classpath*:" + pluginIdentifier + "/" + path
                    + "/*.properties");
            Pattern pattern = Pattern.compile("([a-z][a-zA-Z0-9]*)\\_\\w+\\.properties");

            for (Resource resource : resources) {
                Matcher matcher = pattern.matcher(resource.getFilename());

                if (matcher.matches()) {
                    basenamesInDirectory.add("classpath:" + pluginIdentifier + "/" + path + "/" + matcher.group(1));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot find localization resources", e);
        }

        return basenamesInDirectory;
    }
}
