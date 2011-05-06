package com.qcadoo.view.internal.ribbon;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.RibbonComboBox;

public class RibbonComboBoxImpl extends RibbonActionItemImpl implements RibbonComboBox {

    private List<String> options = new LinkedList<String>();

    @Override
    public void addOption(String option) {
        options.add(option);
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        JSONObject obj = super.getAsJson();
        JSONArray optionsArray = new JSONArray();
        for (String option : options) {
            optionsArray.put(option);
        }
        obj.put("options", optionsArray);
        return obj;
    }

    @Override
    public InternalRibbonActionItem getCopy() {
        RibbonComboBoxImpl copy = new RibbonComboBoxImpl();
        copyFields(copy);
        for (String option : options) {
            copy.addOption(option);
        }
        return copy;
    }
}
