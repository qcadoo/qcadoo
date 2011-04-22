package com.qcadoo.view.api.components;

import java.util.List;
import java.util.Set;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.view.api.ComponentState;

public interface GridComponent extends ComponentState {

    Set<Long> getSelectedEntitiesId();

    void setSelectedEntitiesId(final Set<Long> selectedEntities);

    void setEntities(List<Entity> entities);

    List<Entity> getEntities();

    void setSelectedEntityId(Long selectedEntityId);

    Long getSelectedEntityId();

    void setCustomRestriction(CustomRestriction customRestriction);

    void setIsEditable(boolean isEditable);

}