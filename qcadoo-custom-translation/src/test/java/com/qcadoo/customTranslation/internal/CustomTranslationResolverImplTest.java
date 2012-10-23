package com.qcadoo.customTranslation.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.customTranslation.api.CustomTranslationCacheService;
import com.qcadoo.customTranslation.api.CustomTranslationResolver;

@Ignore
public class CustomTranslationResolverImplTest {

    private CustomTranslationResolver customTranslationResolver;

    @Mock
    private CustomTranslationCacheService customTranslationCacheService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        customTranslationResolver = new CustomTranslationResolverImpl();

        ReflectionTestUtils.setField(customTranslationResolver, "customTranslationCacheService", customTranslationCacheService);
    }

    @Test
    public void shouldReturnTrueWhenIsCustomTranlationActive() {
        // given
        String key = "key";
        Locale locale = new Locale("pl");

        given(customTranslationCacheService.isCustomTranslationActive(key, locale.getLanguage())).willReturn(true);

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

        given(customTranslationCacheService.isCustomTranslationActive(key, locale.getLanguage())).willReturn(false);
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

        given(customTranslationCacheService.getCustomTranslation(key, locale.getLanguage())).willReturn(null);

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

        given(customTranslationCacheService.getCustomTranslation(key, locale.getLanguage())).willReturn(translation);

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

        given(customTranslationCacheService.getCustomTranslation(key, locale.getLanguage())).willReturn(translation);

        MessageFormat messageFormat = new MessageFormat(translation);

        Object[] argsToUse = args;

        translation = messageFormat.format(argsToUse);

        // when
        String result = customTranslationResolver.getCustomTranslation(key, locale, args);

        // then
        assertEquals(translation, result);
    }

}
