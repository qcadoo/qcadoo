package com.qcadoo.model.api.utils;

import java.util.List;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.internal.DetachedEntityTreeImpl;

public final class EntityTreeUtils {

    private EntityTreeUtils() {
    }

    public static EntityTree getDetachedEntityTree(final List<Entity> entities) {
        return new DetachedEntityTreeImpl(entities);
    }

}
