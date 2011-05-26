package com.qcadoo.view.internal.ribbon.templates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RibbonTemplateParameters {

    private final String templatePlugin;

    private final String templateName;

    private final Set<String> includeGroups;

    private final Set<String> includeItems;

    private final Set<String> excludeGroups;

    private final Set<String> excludeItems;

    private RibbonTemplateParameters(final String templatePlugin, final String templateName, final Set<String> includeGroups,
            final Set<String> includeItems, final Set<String> excludeGroups, final Set<String> excludeItems) {
        super();
        if (templatePlugin == null) {
            this.templatePlugin = "qcadooView";
        } else {
            this.templatePlugin = templatePlugin;
        }
        this.templateName = templateName;
        this.includeGroups = includeGroups;
        this.includeItems = includeItems;
        this.excludeGroups = excludeGroups;
        this.excludeItems = excludeItems;
    }

    public static class RibbonTemplateParametersBuilder {

        private final String templatePlugin;

        private final String templateName;

        private Set<String> includeGroups;

        private Set<String> includeItems;

        private Set<String> excludeGroups;

        private Set<String> excludeItems;

        private RibbonTemplateParametersBuilder(final String templatePlugin, final String templateName) {
            this.templatePlugin = templatePlugin;
            this.templateName = templateName;
        }

        public RibbonTemplateParametersBuilder usingOnlyGroups(final String groups) {
            includeGroups = parseNames(groups);
            return this;
        }

        public RibbonTemplateParametersBuilder withoutGroups(final String groups) {
            excludeGroups = parseNames(groups);
            return this;
        }

        public RibbonTemplateParametersBuilder usingOnlyItems(final String items) {
            includeItems = parseNames(items);
            return this;
        }

        public RibbonTemplateParametersBuilder withoutItems(final String items) {
            excludeItems = parseNames(items);
            return this;
        }

        public RibbonTemplateParameters build() {
            return new RibbonTemplateParameters(templatePlugin, templateName, includeGroups, includeItems, excludeGroups,
                    excludeItems);
        }

        private Set<String> parseNames(final String names) {
            return new HashSet<String>(Arrays.asList(names.split(",")));
        }

    }

    public static RibbonTemplateParametersBuilder getBuilder(final String templatePlugin, final String templateName) {
        return new RibbonTemplateParametersBuilder(templatePlugin, templateName);
    }

    public String getTemplatePlugin() {
        return templatePlugin;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Set<String> getIncludeGroups() {
        return includeGroups;
    }

    public Set<String> getIncludeItems() {
        return includeItems;
    }

    public Set<String> getExcludeGroups() {
        return excludeGroups;
    }

    public Set<String> getExcludeItems() {
        return excludeItems;
    }

}
