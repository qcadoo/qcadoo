package com.qcadoo.view.internal.ribbon.model;

import java.util.Collections;
import java.util.List;

public class SingleRibbonGroupPack implements RibbonGroupsPack {

    private final InternalRibbonGroup group;

    public SingleRibbonGroupPack(final InternalRibbonGroup group) {
        this.group = group;
    }

    @Override
    public List<InternalRibbonGroup> getGroups() {
        return Collections.singletonList(group);
    }

    @Override
    public InternalRibbonGroup getGroupByName(final String groupName) {
        if (group.getName().equals(groupName)) {
            return group;
        }
        return null;
    }

    @Override
    public RibbonGroupsPack getCopy() {
        return new SingleRibbonGroupPack(group.getCopy());
    }

    @Override
    public RibbonGroupsPack getUpdate() {
        InternalRibbonGroup diffGroup = group.getUpdate();
        if (diffGroup != null) {
            return new SingleRibbonGroupPack(group.getUpdate());
        }
        return null;
    }
}
