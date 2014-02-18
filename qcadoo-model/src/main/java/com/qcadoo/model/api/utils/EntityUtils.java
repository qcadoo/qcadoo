package com.qcadoo.model.api.utils;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.qcadoo.model.api.Entity;

public class EntityUtils {

    private EntityUtils() {

    }

    private static final Function<Entity, Long> FUNC_EXTRACT_ID = new Function<Entity, Long>() {

        @Override
        public Long apply(final Entity entity) {
            if (entity != null) {
                return entity.getId();
            }
            return null;
        }
    };

    public static Function<Entity, Long> getIdExtractor() {
        return FUNC_EXTRACT_ID;
    }

    public static Collection<Long> getIdsView(final Collection<Entity> entities) {
        return Collections2.transform(entities, getIdExtractor());
    }

    public static <T> Collection<T> getFieldsView(final Collection<Entity> entities, final String fieldName) {
        return Collections2.transform(entities, new Function<Entity, T>() {

            @Override
            public T apply(final Entity input) {
                if (input != null) {
                    return (T) input.getField(fieldName);
                }
                return null;
            }
        });
    }

}
