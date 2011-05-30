package com.qcadoo.view.internal.ribbon.model;

import java.util.List;

public interface RibbonGroupsPack {

    List<InternalRibbonGroup> getGroups();

    InternalRibbonGroup getGroupByName(String groupName);

    RibbonGroupsPack getCopy();

    RibbonGroupsPack getUpdate();

}
