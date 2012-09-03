package com.qcadoo.view.api.components;

import com.qcadoo.model.api.Entity;

public interface LookupComponent extends FieldComponent {

    /**
     * Returns entity which is selected in lookup
     * 
     * @return entity which is selected in lookup or null when entity is not found in database
     */
    Entity getEntity();

}
