package com.qcadoo.model.internal.types;

import com.qcadoo.model.api.types.FieldType;

public abstract class AbstractFieldType implements FieldType {

    private final boolean copyable;

    public AbstractFieldType(final boolean isCopyable) {
        this.copyable = isCopyable;
    }

    @Override
    public boolean isCopyable() {
        return copyable;
    }

}
