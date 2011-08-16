/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.5
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
        if (basename == null || "*".equals(basename)) {
            basenames.addAll(getAllFilesFromPath());
        } else {
            basenames.add("classpath:" + pluginIdentifier + "/" + path + "/" + basename);
        }

        translationModuleService.addTranslationModule(basenames);
    }

    @Override
    public void disable() {
        // if (basename == null || "*".equals(basename)) {
        // basenames.removeAll(getAllFilesFromPath());
        // } else {
        // basenames.remove("classpath:" + pluginIdentifier + "/" + path + "/" + basename);
        // }

        translationModuleService.removeTranslationModule(basenames);
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
