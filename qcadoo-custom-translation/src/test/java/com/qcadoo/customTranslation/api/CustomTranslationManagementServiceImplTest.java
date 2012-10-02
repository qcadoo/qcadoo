package com.qcadoo.customTranslation.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchCriterion;
import com.qcadoo.model.api.search.SearchResult;

public class CustomTranslationManagementServiceImplTest {

    private CustomTranslationManagementService customTranslationManagementService;

    @Mock
    private DataDefinitionService dataDefinitionService;

    @Mock
    private DataDefinition customTranslationDD;

    @Mock
    private Entity customTranslation;

    @Mock
    private SearchCriteriaBuilder searchCriteriaBuilder;

    @Mock
    private SearchResult searchResult;

    @Mock
    private List<Entity> customTranslations;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationManagementService = new CustomTranslationManagementServiceImpl();

        ReflectionTestUtils.setField(customTranslationManagementService, "dataDefinitionService", dataDefinitionService);

        given(
                dataDefinitionService.get(CustomTranslationContants.PLUGIN_IDENTIFIER,
                        CustomTranslationContants.MODEL_CUSTOM_TRANSLATION)).willReturn(customTranslationDD);
    }

    @Test
    public void shouldAddWhenAddCustomTranslationIfCustomTranslationIsNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(null);

        given(customTranslation.getDataDefinition()).willReturn(customTranslationDD);
        given(customTranslationDD.create()).willReturn(customTranslation);

        // when
        customTranslationManagementService.addCustomTranslation(pluginIdentifier, key, locale);

        // then
        verify(customTranslation, times(4)).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD).save(Mockito.any(Entity.class));
    }

    @Test
    public void shouldntAddWhenAddCustomTranslationIfCustomTranslationIsntNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(customTranslation);

        // when
        customTranslationManagementService.addCustomTranslation(pluginIdentifier, key, locale);

        // then
        verify(customTranslation, never()).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD, never()).save(Mockito.any(Entity.class));
    }

    @Test
    public void shouldRemoveWhenRemoveCustomTranslationIfCustomTranslationIsntNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(customTranslation);

        given(customTranslation.getDataDefinition()).willReturn(customTranslationDD);

        // when
        customTranslationManagementService.removeCustomTranslation(pluginIdentifier, key, locale);

        // then
        verify(customTranslation, times(1)).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD).save(Mockito.any(Entity.class));
    }

    @Test
    public void shouldntRemoveWhenRemoveCustomTranslationIfCustomTranslationIsNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(null);

        // when
        customTranslationManagementService.removeCustomTranslation(pluginIdentifier, key, locale);

        // then
        verify(customTranslation, never()).setField(Mockito.anyString(), Mockito.any());
        verify(customTranslationDD, never()).save(Mockito.any(Entity.class));
    }

    @Test
    public void shouldReturnNullWhenGetCustomTranslationIfCustomTranslationIsNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(null);

        // when
        Entity result = customTranslationManagementService.getCustomTranslation(pluginIdentifier, key, locale);

        // then
        assertEquals(null, result);
    }

    @Test
    public void shouldReturnCustomTranslationWhenGetCustomTranslationIfCustomTranslationIsntNull() {
        // given
        String pluginIdentifier = "plugin";
        String key = "key";
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(customTranslation);

        // when
        Entity result = customTranslationManagementService.getCustomTranslation(pluginIdentifier, key, locale);

        // then
        assertEquals(customTranslation, result);
    }

    @Test
    public void shouldReturnNullWhenGetCustomTranslationsIfCustomTranslationsAreNull() {
        // given
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.list()).willReturn(searchResult);
        given(searchResult.getEntities()).willReturn(null);

        // when
        List<Entity> result = customTranslationManagementService.getCustomTranslations(locale);

        // then
        assertEquals(null, result);
    }

    @Test
    public void shouldReturnCustomTranslationsWhenGetCustomTranslationsIfCustomTranslationsArentNull() {
        // given
        String locale = "pl";

        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.list()).willReturn(searchResult);
        given(searchResult.getEntities()).willReturn(customTranslations);

        // when
        List<Entity> result = customTranslationManagementService.getCustomTranslations(locale);

        // then
        assertEquals(customTranslations, result);
    }

    @Test
    public void shouldReturnCustomTranslationDD() {
        // given

        // when
        DataDefinition result = customTranslationManagementService.getCustomTranslationDD();

        // then
        assertEquals(customTranslationDD, result);
    }

}
