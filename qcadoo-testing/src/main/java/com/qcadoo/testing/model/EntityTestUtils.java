package com.qcadoo.testing.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;

/**
 * This is a set of common entity testing helpers.
 * 
 * @since 1.2.1
 */
public final class EntityTestUtils {

    private EntityTestUtils() {
    }

    public static Entity mockEntity() {
        return Mockito.mock(Entity.class);
    }

    public static Entity mockEntity(final DataDefinition dataDefinition) {
        Entity entityMock = mockEntity();
        BDDMockito.given(entityMock.getDataDefinition()).willReturn(dataDefinition);
        return entityMock;
    }

    public static void stubId(final Entity entity, final Long id) {
        BDDMockito.given(entity.getId()).willReturn(id);
        stubField(entity, "id", id);
    }

    public static void stubField(final Entity entity, final String fieldName, final Object fieldValue) {
        BDDMockito.given(entity.getField(fieldName)).willReturn(fieldValue);
    }

    public static void stubField(final Entity entity, final String fieldName, final Answer<?> answer) {
        BDDMockito.given(entity.getField(fieldName)).willAnswer(answer);
    }

    public static void stubStringField(final Entity entity, final String fieldName, final String fieldValue) {
        BDDMockito.given(entity.getStringField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

    public static void stubDateField(final Entity entity, final String fieldName, final Date fieldValue) {
        BDDMockito.given(entity.getDateField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

    public static void stubIntegerField(final Entity entity, final String fieldName, final Integer fieldValue) {
        BDDMockito.given(entity.getIntegerField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

    public static void stubDecimalField(final Entity entity, final String fieldName, final BigDecimal fieldValue) {
        BDDMockito.given(entity.getDecimalField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

    public static void stubManyToManyField(final Entity entity, final String fieldName, final Set<Entity> fieldValue) {
        Answer<Set<Entity>> answer = new Answer<Set<Entity>>() {

            @Override
            public Set<Entity> answer(final InvocationOnMock invocation) throws Throwable {
                return Sets.newHashSet(fieldValue);
            }
        };
        BDDMockito.given(entity.getManyToManyField(fieldName)).willAnswer(answer);
        stubField(entity, fieldName, answer);
    }

    public static void stubHasManyField(final Entity entity, final String fieldName, final Iterable<Entity> fieldValue) {
        EntityList entityListMock = EntityListMock.create(Lists.newArrayList(fieldValue));
        stubHasManyField(entity, fieldName, entityListMock);
    }

    public static void stubHasManyField(final Entity entity, final String fieldName, final EntityList fieldValue) {
        BDDMockito.given(entity.getHasManyField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

    public static void stubBelongsToField(final Entity entity, final String fieldName, final Entity fieldValue) {
        BDDMockito.given(entity.getBelongsToField(fieldName)).willReturn(fieldValue);
        stubField(entity, fieldName, fieldValue);
    }

}
