package com.qcadoo.view.internal.components.awesomeDynamicList;

import java.util.Map.Entry;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.internal.ProxyEntity;

public class AwesomeDynamicListModelUtilsImpl implements AwesomeDynamicListModelUtils {

    public final void proxyBelongsToFields(final Entity entity) {
        DataDefinition dataDefinition = entity.getDataDefinition();
        for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
            if (fieldDefinitionEntry.getValue().getType() instanceof BelongsToType) {
                proxyBelongsToField(fieldDefinitionEntry.getValue(), entity);
            }
        }
    }

    private void proxyBelongsToField(final FieldDefinition fieldDefinition, final Entity entity) {
        Long belongsToEntityId = getBelongsToEntityId(entity.getField(fieldDefinition.getName()));
        if (belongsToEntityId == null) {
            return;
        }
        DataDefinition belongsToDataDefinition = ((BelongsToType) fieldDefinition.getType()).getDataDefinition();
        Entity belongsToEntity = new ProxyEntity(belongsToDataDefinition, belongsToEntityId);
        entity.setField(fieldDefinition.getName(), belongsToEntity);
    }

    private Long getBelongsToEntityId(final Object value) {
        if (value instanceof String) {
            return Long.valueOf((String) value);
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Integer) {
            return Long.valueOf((Integer) value);
        }
        if (value instanceof Entity) {
            return ((Entity) value).getId();
        }
        return null;
    }
}
