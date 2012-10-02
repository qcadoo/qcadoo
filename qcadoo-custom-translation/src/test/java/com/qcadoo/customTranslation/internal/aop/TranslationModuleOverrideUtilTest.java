package com.qcadoo.customTranslation.internal.aop;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.customTranslation.api.CustomTranslationManagementService;
import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.plugin.api.PluginStateResolver;

public class TranslationModuleOverrideUtilTest {

    private TranslationModuleOverrideUtil translationModuleOverrideUtil;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private TranslationService translationService;

    @Mock
    private PluginStateResolver pluginStateResolver;

    @Mock
    private CustomTranslationManagementService customTranslationManagementService;

    @Mock
    private Set<String> basenames;

    @Mock
    private Iterator<String> basenamesIterator;

    @Mock
    private Map<String, String> localesMap;

    @Mock
    private Set<String> locales;

    @Mock
    private Iterator<String> localesIterator;

    @Mock
    private List<Resource> resources;

    @Mock
    private Iterator<Resource> resourcesIterator;

    @Mock
    private Set<Object> keys;

    @Mock
    private Iterator<Object> keysIterator;

    @Mock
    private Resource resource;

    @Mock
    private InputStream inputStream;

    @Mock
    private Properties properties;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        translationModuleOverrideUtil = new TranslationModuleOverrideUtil();

        ReflectionTestUtils.setField(translationModuleOverrideUtil, "applicationContext", applicationContext);
        ReflectionTestUtils.setField(translationModuleOverrideUtil, "translationService", translationService);
        ReflectionTestUtils.setField(translationModuleOverrideUtil, "pluginStateResolver", pluginStateResolver);
        ReflectionTestUtils.setField(translationModuleOverrideUtil, "customTranslationManagementService",
                customTranslationManagementService);
    }

    @Test
    public void shouldReturnTrueWhenShouldOverride() {
        // given
        ReflectionTestUtils.setField(translationModuleOverrideUtil, "useCustomTranslations", true);

        given(pluginStateResolver.isEnabled(CustomTranslationContants.PLUGIN_IDENTIFIER)).willReturn(true);

        // when
        boolean result = translationModuleOverrideUtil.shouldOverride();

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenShouldOverride() {
        // given
        ReflectionTestUtils.setField(translationModuleOverrideUtil, "useCustomTranslations", false);

        given(pluginStateResolver.isEnabled(CustomTranslationContants.PLUGIN_IDENTIFIER)).willReturn(false);

        // when
        boolean result = translationModuleOverrideUtil.shouldOverride();

        // then
        assertFalse(result);
    }

    @Test
    public void shouldntAddTranslationKeysForPluginIfLocalesAreEmpty() {
        // given
        String pluginIdentifier = "plugin";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(false);

        given(locales.iterator()).willReturn(localesIterator);

        // when
        translationModuleOverrideUtil.addTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).addCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    @Test
    public void shouldntAddTranslationKeysForPluginIfResourcesAreEmpty() {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(false);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resourcesIterator.hasNext()).willReturn(false);

        given(resources.iterator()).willReturn(resourcesIterator);

        // when
        translationModuleOverrideUtil.addTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).addCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldntAddTranslationKeysForPluginIfKeysAreEmpty() throws IOException {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";
        String basename = "basename";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(basename);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resource.getInputStream()).willReturn(inputStream);

        given(resourcesIterator.hasNext()).willReturn(true, false);
        given(resourcesIterator.next()).willReturn(resource);

        given(resources.iterator()).willReturn(resourcesIterator);

        given(keysIterator.hasNext()).willReturn(false);

        given(keys.iterator()).willReturn(keysIterator);

        given(properties.keySet()).willReturn(keys);

        // when
        translationModuleOverrideUtil.addTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).addCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldAddTranslationKeysForPlugin() throws IOException {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";
        String basename = "basename";
        String key = "key";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(basename);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resource.getInputStream()).willReturn(inputStream);

        given(resourcesIterator.hasNext()).willReturn(true, false);
        given(resourcesIterator.next()).willReturn(resource);

        given(resources.iterator()).willReturn(resourcesIterator);

        given(keysIterator.hasNext()).willReturn(true, false);
        given(keysIterator.next()).willReturn(key);

        given(keys.iterator()).willReturn(keysIterator);

        given(properties.keySet()).willReturn(keys);

        // when
        translationModuleOverrideUtil.addTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService).addCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    @Test
    public void shouldntRemoveTranslationKeysForPluginIfLocalesAreEmpty() {
        // given
        String pluginIdentifier = "plugin";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(false);

        given(locales.iterator()).willReturn(localesIterator);

        // when
        translationModuleOverrideUtil.removeTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).removeCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    @Test
    public void shouldntRemoveTranslationKeysForPluginIfResourcesAreEmpty() {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(false);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resourcesIterator.hasNext()).willReturn(false);

        given(resources.iterator()).willReturn(resourcesIterator);

        // when
        translationModuleOverrideUtil.removeTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).removeCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldntRemoveTranslationKeysForPluginIfKeysAreEmpty() throws IOException {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";
        String basename = "basename";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(basename);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resource.getInputStream()).willReturn(inputStream);

        given(resourcesIterator.hasNext()).willReturn(true, false);
        given(resourcesIterator.next()).willReturn(resource);

        given(resources.iterator()).willReturn(resourcesIterator);

        given(keysIterator.hasNext()).willReturn(false);

        given(keys.iterator()).willReturn(keysIterator);

        given(properties.keySet()).willReturn(keys);

        // when
        translationModuleOverrideUtil.removeTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService, never()).removeCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    // TODO lupo fix problem with test
    @Ignore
    @Test
    public void shouldRemoveTranslationKeysForPlugin() throws IOException {
        // given
        String pluginIdentifier = "plugin";

        String locale = "pl";
        String basename = "basename";
        String key = "key";

        given(translationService.getLocales()).willReturn(localesMap);
        given(localesMap.keySet()).willReturn(locales);

        given(localesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(locale);

        given(locales.iterator()).willReturn(localesIterator);

        given(basenamesIterator.hasNext()).willReturn(true, false);
        given(localesIterator.next()).willReturn(basename);

        given(basenames.iterator()).willReturn(basenamesIterator);

        given(resource.getInputStream()).willReturn(inputStream);

        given(resourcesIterator.hasNext()).willReturn(true, false);
        given(resourcesIterator.next()).willReturn(resource);

        given(resources.iterator()).willReturn(resourcesIterator);

        given(keysIterator.hasNext()).willReturn(true, false);
        given(keysIterator.next()).willReturn(key);

        given(keys.iterator()).willReturn(keysIterator);

        given(properties.keySet()).willReturn(keys);

        // when
        translationModuleOverrideUtil.removeTranslationKeysForPlugin(pluginIdentifier, basenames);

        // then
        verify(customTranslationManagementService).removeCustomTranslation(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

}
