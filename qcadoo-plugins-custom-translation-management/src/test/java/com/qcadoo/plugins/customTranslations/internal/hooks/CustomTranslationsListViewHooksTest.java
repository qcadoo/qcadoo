package com.qcadoo.plugins.customTranslations.internal.hooks;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.ribbon.Ribbon;
import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.api.ribbon.RibbonGroup;
import com.qcadoo.view.internal.components.window.WindowComponentState;

public class CustomTranslationsListViewHooksTest {

    private static final String L_GRID = "grid";

    private static final String L_WINDOW = "window";

    private static final String L_CLEAN = "clean";

    private static final String L_CLEAN_CUSTOM_TRANSLATIONS = "cleanCustomTranslations";

    private CustomTranslationsListViewHooks customTranslationsListViewHooks;

    @Mock
    private ViewDefinitionState view;

    @Mock
    private GridComponent customTranslationsGrid;

    @Mock
    private FieldComponent localeField;

    @Mock
    private WindowComponentState window;

    @Mock
    private Ribbon ribbon;

    @Mock
    private RibbonGroup clean;

    @Mock
    private RibbonActionItem cleanCustomTranslations;

    @Mock
    private List<Entity> customTranslations;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationsListViewHooks = new CustomTranslationsListViewHooks();
    }

    @Test
    public void shouldUpdateRibbonStateIfCustomTranslationIsSelected() {
        // given
        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);
        given(customTranslationsGrid.getSelectedEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(false);

        given(view.getComponentByReference(L_WINDOW)).willReturn((ComponentState) window);

        given(window.getRibbon()).willReturn(ribbon);

        given(ribbon.getGroupByName(L_CLEAN)).willReturn(clean);

        given(clean.getItemByName(L_CLEAN_CUSTOM_TRANSLATIONS)).willReturn(cleanCustomTranslations);

        // when
        customTranslationsListViewHooks.updateRibbonState(view);

        // then
        verify(cleanCustomTranslations).setEnabled(true);
    }

    @Test
    public void shouldntUpdateRibbonStateIfCustomTranslationIsntSelected() {
        // given
        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);
        given(customTranslationsGrid.getSelectedEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(true);

        given(view.getComponentByReference(L_WINDOW)).willReturn((ComponentState) window);

        given(window.getRibbon()).willReturn(ribbon);

        given(ribbon.getGroupByName(L_CLEAN)).willReturn(clean);

        given(clean.getItemByName(L_CLEAN_CUSTOM_TRANSLATIONS)).willReturn(cleanCustomTranslations);

        // when
        customTranslationsListViewHooks.updateRibbonState(view);

        // then
        verify(cleanCustomTranslations).setEnabled(false);
    }

    @Test
    public void shouldSetLocaleToDefaultIfLocaleIsNull() {
        // given
        given(view.getComponentByReference(LOCALE)).willReturn(localeField);

        given(localeField.getFieldValue()).willReturn(null);

        // when
        customTranslationsListViewHooks.setLocaleToDefault(view);

        // then
        verify(localeField).setFieldValue(Mockito.anyString());
    }

    @Test
    public void shouldntSetLocaleToDefaultIfLocaleIsntNull() {
        // given
        String locale = "pl";

        given(view.getComponentByReference(LOCALE)).willReturn(localeField);

        given(localeField.getFieldValue()).willReturn(locale);

        // when
        customTranslationsListViewHooks.setLocaleToDefault(view);

        // then
        verify(localeField, never()).setFieldValue(Mockito.anyString());
    }

    @Test
    public void shouldSetCustomRestrictionsToNullWhenAddDiscriminatorRestrictionToGridIfLocaleIsEmpty() {
        // given
        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);

        given(view.getComponentByReference(LOCALE)).willReturn(localeField);

        given(localeField.getFieldValue()).willReturn(null);

        // when
        customTranslationsListViewHooks.addDiscriminatorRestrictionToGrid(view);

        // then
        verify(customTranslationsGrid).setCustomRestriction(null);
    }

    @Test
    public void shouldSetCustomRestrictionsWhenAddDiscriminatorRestrictionToGridIfLocaleIsntEmpty() {
        // given
        String locale = "pl";

        given(view.getComponentByReference(L_GRID)).willReturn(customTranslationsGrid);

        given(view.getComponentByReference(LOCALE)).willReturn(localeField);

        given(localeField.getFieldValue()).willReturn(locale);

        // when
        customTranslationsListViewHooks.addDiscriminatorRestrictionToGrid(view);

        // then
        verify(customTranslationsGrid).setCustomRestriction(Mockito.any(CustomRestriction.class));
    }

}
