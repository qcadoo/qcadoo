package com.qcadoo.customTranslation.internal;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.CUSTOM_TRANSLATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.customTranslation.api.CustomTranslationResolver;
import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchCriterion;
import com.qcadoo.model.api.search.SearchResult;

public class CustomTranslationResolverImplTest {

    private CustomTranslationResolver customTranslationResolver;

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

        customTranslationResolver = new CustomTranslationResolverImpl();

        ReflectionTestUtils.setField(customTranslationResolver, "dataDefinitionService", dataDefinitionService);

        given(
                dataDefinitionService.get(CustomTranslationContants.PLUGIN_IDENTIFIER,
                        CustomTranslationContants.MODEL_CUSTOM_TRANSLATION)).willReturn(customTranslationDD);
        given(customTranslationDD.find()).willReturn(searchCriteriaBuilder);
    }

    @Test
    public void shouldReturnTrueWhenIsCustomTranlationActive() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");

        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.list()).willReturn(searchResult);
        given(searchResult.getEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(false);

        // when
        boolean result = customTranslationResolver.isCustomTranslationActive(key, locale);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturFalseWhenIsCustomTranslationActive() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");

        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.list()).willReturn(searchResult);
        given(searchResult.getEntities()).willReturn(customTranslations);
        given(customTranslations.isEmpty()).willReturn(true);

        // when
        boolean result = customTranslationResolver.isCustomTranslationActive(key, locale);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldRetrunNullWhenGetCustomTranslationIfCustomTranslationIsNull() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");

        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(null);

        // when
        String translation = customTranslationResolver.getCustomTranslation(key, locale, null);

        // then
        assertEquals(null, translation);
    }

    @Test
    public void shouldRetrunTranslationWhenGetCustomTranslationIfCustomTranslationIsntNullAndArgsAreNull() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");
        String[] args = null;

        String translation = "translation";

        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(customTranslation);

        given(customTranslation.getStringField(CUSTOM_TRANSLATION)).willReturn(translation);

        MessageFormat messageFormat = new MessageFormat(translation);

        Object[] argsToUse = args;

        translation = messageFormat.format(argsToUse);

        // when
        String result = customTranslationResolver.getCustomTranslation(key, locale, args);

        // then
        assertEquals(translation, result);
    }

    @Test
    public void shouldRetrunTranslationWhenGetCustomTranslationIfCustomTranslationIsntNullArgsArentNull() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");
        String[] args = { "args" };

        String translation = "translation";

        given(searchCriteriaBuilder.add(Mockito.any(SearchCriterion.class))).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.setMaxResults(1)).willReturn(searchCriteriaBuilder);
        given(searchCriteriaBuilder.uniqueResult()).willReturn(customTranslation);

        given(customTranslation.getStringField(CUSTOM_TRANSLATION)).willReturn(translation);

        MessageFormat messageFormat = new MessageFormat(translation);

        Object[] argsToUse = args;

        translation = messageFormat.format(argsToUse);

        // when
        String result = customTranslationResolver.getCustomTranslation(key, locale, args);

        // then
        assertEquals(translation, result);
    }

}
