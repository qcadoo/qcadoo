package com.qcadoo.view.internal.ribbon.templates.model;

import java.util.LinkedList;
import java.util.List;

import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.api.ribbon.RibbonComboItem;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.RibbonUtils;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonActionItem;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.model.RibbonGroupImpl;
import com.qcadoo.view.internal.ribbon.templates.RibbonTemplateParameters;

public class TemplateRibbonGroup {

    private final String name;

    private final List<InternalRibbonActionItem> items = new LinkedList<InternalRibbonActionItem>();

    public TemplateRibbonGroup(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addActionItem(final InternalRibbonActionItem item) {
        items.add(item);
    }

    public InternalRibbonGroup getRibbonGroup(final RibbonTemplateParameters parameters, final ViewDefinition viewDefinition) {
        List<InternalRibbonActionItem> itemsToApply = getFilteredList(parameters);
        if (itemsToApply.size() == 0) {
            return null;
        }
        InternalRibbonGroup group = new RibbonGroupImpl(name);
        for (InternalRibbonActionItem item : itemsToApply) {
            InternalRibbonActionItem itemCopy = item.getCopy();
            translateRibbonAction(itemCopy, viewDefinition);
            group.addItem(itemCopy);
        }
        return group;
    }

    private void translateRibbonAction(final RibbonActionItem item, final ViewDefinition viewDefinition) {
        InternalRibbonActionItem internalItem = (InternalRibbonActionItem) item;
        String translatedAction = RibbonUtils.translateRibbonAction(internalItem.getAction(), viewDefinition);
        internalItem.setAction(translatedAction);

        if (internalItem instanceof RibbonComboItem) {
            RibbonComboItem comboItem = (RibbonComboItem) internalItem;
            for (RibbonActionItem comboItemElement : comboItem.getItems()) {
                translateRibbonAction(comboItemElement, viewDefinition);
            }
        }
    }

    private List<InternalRibbonActionItem> getFilteredList(final RibbonTemplateParameters parameters) {
        List<InternalRibbonActionItem> filteredList = new LinkedList<InternalRibbonActionItem>();
        if (parameters.getExcludeItems() != null) {
            for (InternalRibbonActionItem item : items) {
                if (!parameters.getExcludeItems().contains(name + "." + item.getName())) {
                    filteredList.add(item);
                }
            }
        } else if (parameters.getIncludeItems() != null) {
            for (InternalRibbonActionItem item : items) {
                if (parameters.getIncludeItems().contains(name + "." + item.getName())) {
                    filteredList.add(item);
                }
            }
        } else {
            filteredList = items;
        }
        return filteredList;
    }

    public boolean containItem(String itemName) {
        for (InternalRibbonActionItem item : items) {
            if (itemName.equals(name + "." + item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TemplateRibbonGroup))
            return false;
        TemplateRibbonGroup other = (TemplateRibbonGroup) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
