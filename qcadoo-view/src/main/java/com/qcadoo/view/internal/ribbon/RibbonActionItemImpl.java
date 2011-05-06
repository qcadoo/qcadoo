package com.qcadoo.view.internal.ribbon;

import org.json.JSONException;
import org.json.JSONObject;

public class RibbonActionItemImpl implements InternalRibbonActionItem {

    private String name;

    private Type type;

    private String icon;

    private String script;

    private String clickAction;

    private Boolean enabled;

    private String message;

    private boolean shouldBeUpdated = false;

    @Override
    public final String getAction() {
        return clickAction;
    }

    @Override
    public final void setAction(final String clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setName(final String name) {
        this.name = name;
    }

    @Override
    public final Type getType() {
        return type;
    }

    @Override
    public final void setType(final Type type) {
        this.type = type;
    }

    @Override
    public final String getIcon() {
        return icon;
    }

    @Override
    public final void setIcon(final String icon) {
        this.icon = icon;
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        JSONObject itemObject = new JSONObject();
        itemObject.put("name", name);
        itemObject.put("type", type);
        itemObject.put("icon", icon);
        itemObject.put("enabled", enabled);
        itemObject.put("message", message);
        if (script != null) {
            itemObject.put("script", script);
        }
        itemObject.put("clickAction", clickAction);
        return itemObject;
    }

    @Override
    public String getScript() {
        return script;
    }

    @Override
    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public InternalRibbonActionItem getCopy() {
        InternalRibbonActionItem copy = new RibbonActionItemImpl();
        copyFields(copy);
        return copy;
    }

    protected void copyFields(InternalRibbonActionItem item) {
        item.setName(name);
        item.setType(type);
        item.setIcon(icon);
        item.setScript(script);
        item.setAction(clickAction);
        item.setEnabled(enabled);
        item.setMessage(message);
    }

    @Override
    public boolean isShouldBeUpdated() {
        return shouldBeUpdated;
    }

    @Override
    public void requestUpdate(boolean shouldBeUpdated) {
        this.shouldBeUpdated = shouldBeUpdated;
    }
}
