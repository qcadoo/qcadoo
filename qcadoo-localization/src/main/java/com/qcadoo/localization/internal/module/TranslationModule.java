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
import com.qcadoo.plugin.api.PluginState;

public class TranslationModule extends Module {

    private ApplicationContext applicationContext;

    private TranslationModuleService translationModuleService;

    private Set<String> basenames = new LinkedHashSet<String>();

    private String pluginIdentifier;

    private String basename;

    private String path;

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
    public void init(PluginState state) {
    }

    @Override
    public void enable() {
        if (basename == null) {
            basenames.addAll(getAllFilesFromPath());
        } else {
            basenames.add("classpath:" + pluginIdentifier + "/" + path + "/" + basename);
        }

        translationModuleService.addTranslationModul(basenames);
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
