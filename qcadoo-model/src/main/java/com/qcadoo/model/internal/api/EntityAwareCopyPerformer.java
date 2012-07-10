package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface EntityAwareCopyPerformer extends Entity {

    Entity copy(final Entity performerEntity);

}
