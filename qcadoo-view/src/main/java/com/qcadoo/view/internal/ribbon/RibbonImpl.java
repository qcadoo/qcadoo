package com.qcadoo.view.internal.ribbon;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.view.api.ribbon.RibbonGroup;

public class RibbonImpl implements InternalRibbon {

    private String name;

    private final List<InternalRibbonGroup> groups = new LinkedList<InternalRibbonGroup>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public List<RibbonGroup> getGroups() {
        return new LinkedList<RibbonGroup>(groups);
    }

    @Override
    public RibbonGroup getGroupByName(String groupName) {
        for (RibbonGroup group : groups) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public void addGroup(final InternalRibbonGroup group) {
        this.groups.add(group);
    }

    @Override
    public void removeGroup(final InternalRibbonGroup group) {
        this.groups.remove(group);
    }

    @Override
    public JSONObject getAsJson() {
        JSONObject ribbonJson = new JSONObject();
        try {
            ribbonJson.put("name", name);
            JSONArray groupsArray = new JSONArray();
            for (InternalRibbonGroup group : groups) {
                if (group.getExtensionPluginIdentifier() == null || PluginUtils.isEnabled(group.getExtensionPluginIdentifier())) {
                    groupsArray.put(group.getAsJson());
                }
            }
            ribbonJson.put("groups", groupsArray);
            return ribbonJson;
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public InternalRibbon getCopy() {
        InternalRibbon copy = new RibbonImpl();
        copy.setName(name);
        for (InternalRibbonGroup group : groups) {
            copy.addGroup(group.getCopy());
        }
        return copy;
    }

    @Override
    public InternalRibbon getUpdate() {
        InternalRibbon diff = new RibbonImpl();
        boolean isDiffrence = false;
        diff.setName(name);

        for (InternalRibbonGroup group : groups) {
            InternalRibbonGroup diffGroup = group.getUpdate();
            if (diffGroup != null) {
                diff.addGroup(diffGroup);
                isDiffrence = true;
            }

        }
        if (isDiffrence) {
            return diff;
        }
        return null;
    }
}
