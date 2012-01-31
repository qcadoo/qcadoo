package com.qcadoo.model.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.ValidationService;

public class ValidationServiceImplTest {

    private ValidationService validationService;

    private Entity genericEntity;

    private Entity existingGenericEntity;

    private InternalDataDefinition dataDefinition;

    @Before
    public final void init() {
        genericEntity = mock(Entity.class);
        existingGenericEntity = mock(Entity.class);
        dataDefinition = mock(InternalDataDefinition.class);

        validationService = new ValidationServiceImpl();
    }

    @Test
    public final void shouldCallCreateHooksIfEntityIsValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(true);
        when(genericEntity.getId()).thenReturn(null);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, existingGenericEntity);

        // then
        verify(dataDefinition).callCreateHook(genericEntity);

    }

    @Test
    public final void shouldCallUpdateHooksIfEntityIsValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(true);
        when(genericEntity.getId()).thenReturn(1L);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, existingGenericEntity);

        // then
        verify(dataDefinition).callUpdateHook(genericEntity);

    }

    @Test
    public final void shouldNotCallModelHooksIfEntityIsNotValid() throws Exception {
        // given
        when(genericEntity.isValid()).thenReturn(false);

        // when
        validationService.validateGenericEntity(dataDefinition, genericEntity, null);

        // then
        verify(dataDefinition, never()).callCreateHook(genericEntity);
        verify(dataDefinition, never()).callUpdateHook(genericEntity);
    }

}
