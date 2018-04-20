package com.qcadoo.view.internal.components.inputWithActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.components.InputWithActionComponent;
import com.qcadoo.view.internal.components.FieldComponentState;

public final class InputWithActionComponentState extends FieldComponentState implements InputWithActionComponent {

    private final InputWithActionEventPerformer eventPerformer = new InputWithActionEventPerformer();

    private boolean enabled = true;

    public InputWithActionComponentState(InputWithActionComponentPattern pattern) {
        super(pattern);
        enabled = pattern.isEnabled();
        registerEvent("onClick", eventPerformer, "onClick");
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = super.renderContent();
        json.put("enabled", enabled);
        return json;

    }
    public void setInputEnabled(boolean enable) {
        enabled = enable;
    }
    protected static class InputWithActionEventPerformer {

        public void onClick(final String[] args) {
            // nothing interesting here
        }

    }
}
