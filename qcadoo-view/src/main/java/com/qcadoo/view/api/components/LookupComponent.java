package com.qcadoo.view.api.components;

import com.qcadoo.model.api.Entity;

public interface LookupComponent extends FieldComponent {

    /**
     * Returns entity with is selected in lookup
     * 
     * @return entity with is selected in lookup
     */
    Entity getEntity();

}
