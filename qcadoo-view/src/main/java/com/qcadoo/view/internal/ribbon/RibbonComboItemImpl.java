package com.qcadoo.view.internal.ribbon;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.RibbonActionItem;

public class RibbonComboItemImpl extends RibbonActionItemImpl implements InternalRibbonComboItem {

    private final List<InternalRibbonActionItem> items = new LinkedList<InternalRibbonActionItem>();

    @Override
    public List<RibbonActionItem> getItems() {
        return new LinkedList<RibbonActionItem>(items);
    }

    @Override
    public void addItem(final InternalRibbonActionItem item) {
        items.add(item);
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        JSONObject itemObject = super.getAsJson();
        JSONArray itemsArray = new JSONArray();
        for (InternalRibbonActionItem item : items) {
            itemsArray.put(item.getAsJson());
        }
        itemObject.put("items", itemsArray);
        return itemObject;
    }

    @Override
    public InternalRibbonActionItem getCopy() {
        RibbonComboItemImpl copy = new RibbonComboItemImpl();
        copyFields(copy);
        for (InternalRibbonActionItem item : items) {
            copy.addItem(item.getCopy());
        }
        return copy;
    }
}
