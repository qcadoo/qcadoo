package com.qcadoo.customTranslation.internal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Maps;
import com.qcadoo.customTranslation.api.CustomTranslationCacheService;

public class CustomTranslationCacheServiceImplTest {

    private CustomTranslationCacheService customTranslationCacheService;

    @Mock
    private Map<String, Map<String, String>> customTranslations;

    @Mock
    private Map<String, String> customTranslation;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationCacheService = new CustomTranslationCacheServiceImpl();
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldAddCustomTranslation() {
        // given
        String key = "key";
        String locale = "pl";
        String translation = "translation";

        Map<String, String> localeAndCustomTranslation = Maps.newHashMap();
        localeAndCustomTranslation.put(locale, translation);

        // when
        customTranslationCacheService.addCustomTranslation(key, locale, translation);

        // then
        verify(customTranslations).put(key, localeAndCustomTranslation);
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldUpdateWhenUpdateCustomTranslationIfContainsKey() {
        // given
        String key = "key";
        String locale = "pl";
        String translation = "translation";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);

        // when
        customTranslationCacheService.updateCustomTranslation(key, locale, translation);

        // then
        verify(customTranslation).put(key, translation);
    }

    @Test
    public void shouldntUpdateWhenUpdateCustomTranslationIfNotContainsKey() {
        // given
        String key = "key";
        String locale = "pl";
        String translation = "translation";

        given(customTranslations.containsKey(key)).willReturn(false);

        // when
        customTranslationCacheService.updateCustomTranslation(key, locale, translation);

        // then
        verify(customTranslation, never()).put(locale, translation);
    }

    @Test
    public void shouldReturnNullWhenGetCustomTranslationIfNotContainsKey() {
        // given
        String key = "key";
        String locale = "pl";

        given(customTranslations.containsKey(key)).willReturn(false);
        // when
        String result = customTranslationCacheService.getCustomTranslation(key, locale);

        // then
        assertEquals(null, result);
    }

    @Test
    public void shouldReturnNullWhenGetCustomTranslationIfContainsKeyAndNotContainsLocale() {
        // given
        String key = "key";
        String locale = "pl";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);
        given(customTranslation.containsKey(locale)).willReturn(false);

        // when
        String result = customTranslationCacheService.getCustomTranslation(key, locale);

        // then
        assertEquals(null, result);
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldReturnCustomTranslationWhenGetCustomTranslationIfContainsKeyAndContainsLocale() {
        // given
        String key = "key";
        String locale = "pl";
        String translation = "translation";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);
        given(customTranslation.containsKey(locale)).willReturn(true);
        given(customTranslation.get(locale)).willReturn(translation);

        // when
        String result = customTranslationCacheService.getCustomTranslation(key, locale);

        // then
        assertEquals(translation, result);
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldGetCustomTranslations() {
        // given

        // when
        Map<String, Map<String, String>> result = customTranslationCacheService.getCustomTranslations();

        // then
        assertEquals(customTranslations, result);
    }

    @Test
    public void shouldReturnFalseWhenIsCustomTranslationAddedIfNotContainsKey() {
        // given
        String key = "key";

        given(customTranslations.containsKey(key)).willReturn(false);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationAdded(key);

        // then
        assertFalse(result);
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldReturnTrueWhenIsCustomTranslationAddedIfContainsKey() {
        // given
        String key = "key";

        given(customTranslations.containsKey(key)).willReturn(false);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationAdded(key);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenIsCustomTranslationActiveIfNotContainsKey() {
        // given
        String key = "key";
        String locale = "pl";

        given(customTranslations.containsKey(key)).willReturn(false);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationActive(key, locale);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenIsCustomTranslationActiveIfContainsKeyAndNotContainsLocale() {
        // given
        String key = "key";
        String locale = "pl";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);
        given(customTranslation.containsKey(locale)).willReturn(false);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationActive(key, locale);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenIsCustomTranslationActiveIfContainsKeyAndContainsLocaleAndCustomTranslationIsNull() {
        // given
        String key = "key";
        String locale = "pl";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);
        given(customTranslation.containsKey(locale)).willReturn(true);
        given(customTranslation.get(locale)).willReturn(null);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationActive(key, locale);

        // then
        assertFalse(result);
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldReturnTrueWhenIsCustomTranslationActiveIfContainsKeyAndContainsLocaleAndCustomTranslationIsntNull() {
        // given
        String key = "key";
        String locale = "pl";
        String translation = "translation";

        given(customTranslations.containsKey(key)).willReturn(true);
        given(customTranslations.get(key)).willReturn(customTranslation);
        given(customTranslation.containsKey(locale)).willReturn(true);
        given(customTranslation.get(locale)).willReturn(translation);

        // when
        boolean result = customTranslationCacheService.isCustomTranslationActive(key, locale);

        // then
        assertTrue(result);
    }

}
