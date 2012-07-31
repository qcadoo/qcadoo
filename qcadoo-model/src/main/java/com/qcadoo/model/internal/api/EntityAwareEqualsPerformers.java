package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface EntityAwareEqualsPerformers extends Entity {

    boolean equals(final Entity obj, final PerformerEntitiesChain performersChain);

    boolean flatEquals(final Entity obj);

}
