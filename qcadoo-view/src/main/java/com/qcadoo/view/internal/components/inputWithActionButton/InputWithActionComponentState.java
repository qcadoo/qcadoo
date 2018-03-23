package com.qcadoo.view.internal.components.inputWithActionButton;

import com.qcadoo.view.internal.components.FieldComponentPattern;
import com.qcadoo.view.internal.components.FieldComponentState;

public final class InputWithActionComponentState extends FieldComponentState {

    private final InputWithActionEventPerformer eventPerformer = new InputWithActionEventPerformer();

    public InputWithActionComponentState(FieldComponentPattern pattern) {
        super(pattern);
        registerEvent("onClick", eventPerformer, "onClick");
    }

    protected class InputWithActionEventPerformer {

        public void onClick(final String[] args) {
            // nothing interesting here
        }

    }
}
