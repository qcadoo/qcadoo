package com.qcadoo.plugins.customTranslations.internal.listeners;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Maps;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ComponentState.MessageType;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;

public class CustomTranslationsListListenersTest {

    private CustomTranslationsListListeners customTranslationsListListeners;

    private static final String L_GRID = "grid";

    private static final String L_WINDOW_ACTIVE_MENU = "window.activeMenu";

    @Mock
    private ViewDefinitionState view;

    @Mock
    private GridComponent customTranslationsGrid;

    @Mock
    private DataDefinition customTranslationDD;

    @Mock
    private Entity customTranslation;

    @Mock
    private List<Entity> customTranslations;

    @Mock
    private Iterator<Entity> customTranslationsIterator;

    private Map<String, Object> parameters = Maps.newHashMap();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationsListListeners = new CustomTranslationsListListeners();
    }

    @Test
    public void shouldCleanCustomTranslationsWhenCustomTranslationIsSelected() {
        // given
        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);
        given(customTranslationsGrid.getSelectedEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(false);

        given(customTranslationsIterator.hasNext()).willReturn(true, false);
        given(customTranslationsIterator.next()).willReturn(customTranslation);

        given(customTranslations.iterator()).willReturn(customTranslationsIterator);

        given(customTranslation.getDataDefinition()).willReturn(customTranslationDD);

        // when
        customTranslationsListListeners.cleanCustomTranslations(view, null, null);

        // then
        verify(customTranslation).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD).save(Mockito.any(Entity.class));
        verify(customTranslationsGrid).addMessage(Mockito.anyString(), Mockito.any(MessageType.class));
    }

    @Test
    public void shouldntCleanCustomTranslationsWhenCustomTranslationIsntSelected() {
        // given
        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);
        given(customTranslationsGrid.getSelectedEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(true);

        // when
        customTranslationsListListeners.cleanCustomTranslations(view, null, null);

        // then
        verify(customTranslation, never()).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD, never()).save(Mockito.any(Entity.class));
        verify(customTranslationsGrid, never()).addMessage(Mockito.anyString(), Mockito.any(MessageType.class));
    }

    @Test
    public void shouldReplaceCustomTranslations() {
        // given
        parameters.put(L_WINDOW_ACTIVE_MENU, "administration.customTranslations");

        String url = "../page/qcadooCustomTranslations/replaceCustomTranslations.html";

        // when
        customTranslationsListListeners.replaceCustomTranslations(view, null, null);

        // then
        verify(view).redirectTo(url, false, true, parameters);
    }

}
