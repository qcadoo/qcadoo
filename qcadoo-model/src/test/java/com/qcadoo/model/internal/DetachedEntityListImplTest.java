package com.qcadoo.model.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;

public class DetachedEntityListImplTest {

    private DetachedEntityListImpl detachedList;

    private DataDefinition dataDefinition;

    private List<Entity> entities;

    @Before
    public final void init() {
        dataDefinition = mock(DataDefinition.class);
        entities = getListOfMockEntities();
        detachedList = new DetachedEntityListImpl(dataDefinition, entities);
    }

    private List<Entity> getListOfMockEntities() {
        Entity e1 = mock(Entity.class);
        Entity e2 = mock(Entity.class);
        Entity e3 = mock(Entity.class);

        when(e1.getId()).thenReturn(1L);
        when(e2.getId()).thenReturn(2L);
        when(e3.getId()).thenReturn(3L);

        return Lists.newArrayList(e1, e2, e3);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void shouldThrowExceptionWhenCallingFindMethod() throws Exception {
        // when
        detachedList.find();
    }

}
