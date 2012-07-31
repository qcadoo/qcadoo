package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface PerformerEntitiesChain {

    void append(final Entity performer);

    Entity find(final Entity entity);

    Entity getLast();
}
