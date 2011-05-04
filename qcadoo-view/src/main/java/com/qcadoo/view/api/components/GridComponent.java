package com.qcadoo.view.api.components;

import java.util.List;
import java.util.Set;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.view.api.ComponentState;

/**
 * Represents grid component
 * 
 * @since 0.4.0
 */
public interface GridComponent extends ComponentState {

    /**
     * Gets ids of all selected entities
     * 
     * @return ids of all selected entities
     */
    Set<Long> getSelectedEntitiesIds();

    /**
     * Sets new collection of selected entities
     * 
     * @param selectedEntities
     *            ids of selected entities
     */
    void setSelectedEntitiesIds(final Set<Long> selectedEntities);

    /**
     * Sets new content of grid
     * 
     * <b>Warning:</b> Paging, searching and sorting can not work when grid content is put directly by this method.
     * 'setCustomRestriction' should be used instead wherever possible
     * 
     * @param entities
     *            new content of grid
     */
    void setEntities(List<Entity> entities);

    /**
     * Returns current content of grid
     * 
     * @return all content entities of this grid
     */
    List<Entity> getEntities();

    /**
     * Adds restriction to this grid
     * 
     * @param customRestriction
     *            additional restriction to this grid
     */
    void setCustomRestriction(CustomRestriction customRestriction);

    /**
     * Defines if this grid should be editable
     * 
     * @param isEditable
     *            true if this grid should be editable, false if not
     */
    void setEditable(boolean isEditable);

}