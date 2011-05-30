package com.qcadoo.view.internal.ribbon.templates.model;

import java.util.List;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.model.RibbonGroupsPack;
import com.qcadoo.view.internal.ribbon.templates.RibbonTemplateParameters;

public class TemplateRibbonGroupsPack implements RibbonGroupsPack {

    private final RibbonTemplate template;

    private final RibbonTemplateParameters parameters;

    private final ViewDefinition viewDefinition;

    public TemplateRibbonGroupsPack(final RibbonTemplate template, final RibbonTemplateParameters parameters,
            final ViewDefinition viewDefinition) {
        this.template = template;
        this.parameters = parameters;
        this.viewDefinition = viewDefinition;
    }

    @Override
    public List<InternalRibbonGroup> getGroups() {
        return template.getRibbonGroups(parameters, viewDefinition);
    }

    @Override
    public InternalRibbonGroup getGroupByName(final String groupName) {
        for (InternalRibbonGroup group : getGroups()) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public RibbonGroupsPack getCopy() {
        // we can return this instance because no permanent changes can be made
        return this;
    }

    @Override
    public RibbonGroupsPack getUpdate() {
        // template cannot be updated
        return null;
    }

}
