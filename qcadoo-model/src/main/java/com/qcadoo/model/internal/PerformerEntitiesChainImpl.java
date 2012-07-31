package com.qcadoo.model.internal;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.PerformerEntitiesChain;

public final class PerformerEntitiesChainImpl implements PerformerEntitiesChain {

    private final List<Entity> performersChain;

    public PerformerEntitiesChainImpl(final Entity performer) {
        Preconditions.checkNotNull(performer);
        this.performersChain = Lists.newArrayList(performer);
    }

    @Override
    public void append(final Entity performer) {
        performersChain.add(performer);
    }

    @Override
    public Entity find(final Entity entity) {
        Preconditions.checkNotNull(entity);
        for (Entity performer : performersChain) {
            if (areShallowEqual(performer, entity)) {
                return performer;
            }
        }
        return null;
    }

    private boolean areShallowEqual(final Entity firstEntity, final Entity secondEntity) {
        return firstEntity.hashCode() == secondEntity.hashCode();
    }

    @Override
    public Entity getLast() {
        return performersChain.get(performersChain.size() - 1);
    }

}
