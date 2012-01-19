/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.2
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
package com.qcadoo.view.internal;

import org.springframework.context.ApplicationContext;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.ViewDefinition;

public final class ComponentDefinition {

    private String name;

    private String fieldPath;

    private String sourceFieldPath;

    private String reference;

    private String extensionPluginIdentifier;

    private boolean defaultEnabled = true;

    private boolean defaultVisible = true;

    private boolean hasDescription;

    private boolean hasLabel;

    private TranslationService translationService;

    private ApplicationContext applicationContext;

    private ViewDefinition viewDefinition;

    private ComponentPattern parent;

    private DataDefinition dataDefinition;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(final String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public String getSourceFieldPath() {
        return sourceFieldPath;
    }

    public void setSourceFieldPath(final String sourceFieldPath) {
        this.sourceFieldPath = sourceFieldPath;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public void setDefaultEnabled(final boolean defaultEnabled) {
        this.defaultEnabled = defaultEnabled;
    }

    public boolean isDefaultVisible() {
        return defaultVisible;
    }

    public void setDefaultVisible(final boolean defaultVisible) {
        this.defaultVisible = defaultVisible;
    }

    public boolean isHasDescription() {
        return hasDescription;
    }

    public void setHasDescription(final boolean hasDescription) {
        this.hasDescription = hasDescription;
    }

    public ComponentPattern getParent() {
        return parent;
    }

    public void setParent(final ComponentPattern parent) {
        this.parent = parent;
    }

    public TranslationService getTranslationService() {
        return translationService;
    }

    public void setTranslationService(final TranslationService translationService) {
        this.translationService = translationService;
    }

    public ViewDefinition getViewDefinition() {
        return viewDefinition;
    }

    public void setViewDefinition(final ViewDefinition viewDefinition) {
        this.viewDefinition = viewDefinition;
    }

    public boolean isHasLabel() {
        return hasLabel;
    }

    public void setHasLabel(final boolean hasLabel) {
        this.hasLabel = hasLabel;
    }

    public DataDefinition getDataDefinition() {
        return dataDefinition;
    }

    public void setDataDefinition(final DataDefinition dataDefinition) {
        this.dataDefinition = dataDefinition;
    }

    public String getExtensionPluginIdentifier() {
        return extensionPluginIdentifier;
    }

    public void setExtensionPluginIdentifier(final String extensionPluginIdentifier) {
        this.extensionPluginIdentifier = extensionPluginIdentifier;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
