package com.qcadoo.view.internal.components.awesomeDynamicList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;

public class AwesomeDynamicListModelUtilsImplTest {

    private AwesomeDynamicListModelUtils awesomeDynamicListModelUtils;

    private FieldDefinition fieldDefinition;

    private Entity entity;

    private DataDefinition belongsToDataDefinition;

    private static final String BELONGS_TO_FIELD_NAME = "belongsToField";

    @Before
    public final void init() {
        awesomeDynamicListModelUtils = new AwesomeDynamicListModelUtilsImpl();

        BelongsToType belongsToType = mock(BelongsToType.class);
        when(belongsToType.getDataDefinition()).thenReturn(belongsToDataDefinition);

        fieldDefinition = mock(FieldDefinition.class);
        when(fieldDefinition.getType()).thenReturn(belongsToType);
        when(fieldDefinition.getName()).thenReturn(BELONGS_TO_FIELD_NAME);

        @SuppressWarnings("unchecked")
        Entry<String, FieldDefinition> fieldEntry = mock(Entry.class);
        when(fieldEntry.getKey()).thenReturn(BELONGS_TO_FIELD_NAME);
        when(fieldEntry.getValue()).thenReturn(fieldDefinition);

        @SuppressWarnings("unchecked")
        Iterator<Entry<String, FieldDefinition>> iterator = mock(Iterator.class);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(fieldEntry);

        @SuppressWarnings("unchecked")
        Set<Entry<String, FieldDefinition>> entrySet = mock(Set.class);
        when(entrySet.iterator()).thenReturn(iterator);
        when(entrySet.size()).thenReturn(1);

        @SuppressWarnings("unchecked")
        Map<String, FieldDefinition> fields = mock(Map.class);
        when(fields.entrySet()).thenReturn(entrySet);

        DataDefinition dataDefinition = mock(DataDefinition.class);
        when(dataDefinition.getFields()).thenReturn(fields);

        entity = mock(Entity.class);
        when(entity.getDataDefinition()).thenReturn(dataDefinition);

        belongsToDataDefinition = mock(DataDefinition.class);
        when(belongsToDataDefinition.getName()).thenReturn("modelName");
        when(belongsToDataDefinition.getPluginIdentifier()).thenReturn("pluginIdentifier");

        when(belongsToType.getDataDefinition()).thenReturn(belongsToDataDefinition);

    }

    @Test
    public final void shouldSetProxyEntityInPlaceOfLong() throws Exception {
        // given
        when(entity.getField(BELONGS_TO_FIELD_NAME)).thenReturn(1L);
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

        // when
        awesomeDynamicListModelUtils.proxyBelongsToFields(entity);

        // then
        verify(entity, Mockito.times(1)).setField(Mockito.eq(BELONGS_TO_FIELD_NAME), entityCaptor.capture());
        assertProxy(entityCaptor.getValue());
    }

    @Test
    public final void shouldSetProxyEntityInPlaceOfInteger() throws Exception {
        // given
        when(entity.getField(BELONGS_TO_FIELD_NAME)).thenReturn(1);
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

        // when
        awesomeDynamicListModelUtils.proxyBelongsToFields(entity);

        // then
        verify(entity, Mockito.times(1)).setField(Mockito.eq(BELONGS_TO_FIELD_NAME), entityCaptor.capture());
        assertProxy(entityCaptor.getValue());
    }

    @Test
    public final void shouldSetProxyEntityInPlaceOfString() throws Exception {
        // given
        when(entity.getField(BELONGS_TO_FIELD_NAME)).thenReturn("1");
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

        // when
        awesomeDynamicListModelUtils.proxyBelongsToFields(entity);

        // then
        verify(entity, Mockito.times(1)).setField(Mockito.eq(BELONGS_TO_FIELD_NAME), entityCaptor.capture());
        assertProxy(entityCaptor.getValue());
    }

    @Test
    public final void shouldSetProxyEntityInPlaceOfEntity() throws Exception {
        // given
        Entity belongsToEntity = mock(Entity.class);
        when(belongsToEntity.getId()).thenReturn(1L);
        when(entity.getField(BELONGS_TO_FIELD_NAME)).thenReturn(belongsToEntity);
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

        // when
        awesomeDynamicListModelUtils.proxyBelongsToFields(entity);

        // then
        verify(entity, Mockito.times(1)).setField(Mockito.eq(BELONGS_TO_FIELD_NAME), entityCaptor.capture());
        assertProxy(entityCaptor.getValue());
    }

    @Test
    public final void shouldSetProxyEntityToNull() throws Exception {
        // when
        awesomeDynamicListModelUtils.proxyBelongsToFields(entity);

        // then
        verify(entity, Mockito.never()).setField(Mockito.eq(BELONGS_TO_FIELD_NAME), Mockito.any(Entity.class));
    }

    private void assertProxy(final Entity proxyEntity) {
        Assert.assertEquals(Long.valueOf(1L), proxyEntity.getId());
        Assert.assertEquals(belongsToDataDefinition.getName(), proxyEntity.getDataDefinition().getName());
        Assert.assertEquals(belongsToDataDefinition.getPluginIdentifier(), proxyEntity.getDataDefinition().getPluginIdentifier());
    }
}
