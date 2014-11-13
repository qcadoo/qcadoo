package com.qcadoo.testing.model;

import org.mockito.Matchers;

import com.qcadoo.model.api.Entity;

public final class QcadooModelMatchers {

    private QcadooModelMatchers() {
    }

    public static final Entity anyEntity() {
        return Matchers.any(Entity.class);
    }

}
