package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface EntityAwareEqualsPerformer extends Entity {

    boolean equals(final Entity obj, final Entity performerEntity);

    boolean flatEquals(final Entity obj);

}
