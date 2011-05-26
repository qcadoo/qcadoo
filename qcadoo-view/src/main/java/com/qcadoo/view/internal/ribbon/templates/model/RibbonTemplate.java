package com.qcadoo.view.internal.ribbon.templates.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.templates.RibbonTemplateParameters;

public class RibbonTemplate {

    private final String name;

    private final String plugin;

    private final List<TemplateRibbonGroup> groups = new ArrayList<TemplateRibbonGroup>();

    public RibbonTemplate(final String plugin, final String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public String getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public void addTemplateGroup(final TemplateRibbonGroup group) {
        if (groups.contains(group)) {
            throw new IllegalStateException("group '" + group.getName() + "' already exists in template '" + name + "'");
        }
        groups.add(group);
    }

    public void removeTemplateGroup(final TemplateRibbonGroup group) {
        groups.remove(group);
    }

    public void applyTemplate(final InternalRibbon ribbon, final RibbonTemplateParameters parameters,
            final ViewDefinition viewDefinition) {
        List<TemplateRibbonGroup> groupsToApply = getFilteredList(parameters);
        for (TemplateRibbonGroup group : groupsToApply) {
            InternalRibbonGroup groupInstance = group.getRibbonGroup(parameters, viewDefinition);
            if (groupInstance != null) {
                ribbon.addGroup(groupInstance);
            }
        }
    }

    private List<TemplateRibbonGroup> getFilteredList(final RibbonTemplateParameters parameters) {
        List<TemplateRibbonGroup> filteredList = new LinkedList<TemplateRibbonGroup>();
        if (parameters.getExcludeGroups() != null) {
            parseGroupNames(parameters.getExcludeGroups());
            for (TemplateRibbonGroup group : groups) {
                if (!parameters.getExcludeGroups().contains(group.getName())) {
                    filteredList.add(group);
                }
            }
        } else if (parameters.getIncludeGroups() != null) {
            parseGroupNames(parameters.getIncludeGroups());
            for (TemplateRibbonGroup group : groups) {
                if (parameters.getIncludeGroups().contains(group.getName())) {
                    filteredList.add(group);
                }
            }
        } else {
            filteredList = groups;
        }
        return filteredList;
    }

    private void parseGroupNames(final Set<String> groupNames) {
        for (String groupName : groupNames) {
            boolean groupFound = false;
            for (TemplateRibbonGroup group : groups) {
                if (groupName.equals(group.getName())) {
                    groupFound = true;
                }
            }
            if (!groupFound) {
                throw new IllegalStateException("group '" + groupName + "' not found in template '" + name + "'");
            }
        }
    }
}
