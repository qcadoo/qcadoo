package com.qcadoo.model.api.units.restrictions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Preconditions;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.internal.ProxyEntity;

public class BelongingTo implements CustomRestriction {

    private final String fieldName;

    private final ProxyEntity entity;

    private final transient int hashCode;

    public BelongingTo(final String fieldName, final Entity entity) {
        Preconditions.checkArgument(fieldName != null, "Field name can't be null.");
        Preconditions.checkArgument(entity != null, "Entity can't be null.");
        Preconditions.checkArgument(entity.getId() != null, "Entity id can't be null.");
        this.fieldName = fieldName;
        this.entity = new ProxyEntity(entity.getDataDefinition(), entity.getId());
        this.hashCode = new HashCodeBuilder().append(fieldName).append(entity.getId())
                .append(entity.getDataDefinition().getPluginIdentifier()).append(entity.getDataDefinition().getName())
                .toHashCode();
    }

    @Override
    public void addRestriction(final SearchCriteriaBuilder searchCriteriaBuilder) {
        searchCriteriaBuilder.add(SearchRestrictions.belongsTo(fieldName, entity.getDataDefinition(), entity.getId()));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BelongingTo that = (BelongingTo) o;
        DataDefinition thisDataDef = this.entity.getDataDefinition();
        DataDefinition thatDataDef = that.entity.getDataDefinition();
        return new EqualsBuilder().append(this.fieldName, that.fieldName).append(this.entity.getId(), that.entity.getId())
                .append(thisDataDef.getName(), thatDataDef.getName())
                .append(thisDataDef.getPluginIdentifier(), thatDataDef.getPluginIdentifier()).isEquals();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
