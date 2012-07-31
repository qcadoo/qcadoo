package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface EntityAwareCopyPerformers extends Entity {

    Entity copy(final PerformerEntitiesChain performersChain);

}
