package com.qcadoo.view.internal.components.select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Maps;
import com.qcadoo.model.api.DictionaryService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.types.DictionaryType;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinition;

public class SelectComponentStateTest {

    private static final String DICTIONARY_NAME = "dictionary";

    private static final String VALUES = "values";

    private static final String KEY = "key";

    private SelectComponentState componentState;

    @Mock
    private FieldDefinition fieldDefinition;

    @Mock
    private DictionaryService dictionaryService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        DictionaryType dictionaryType = new DictionaryType(DICTIONARY_NAME, dictionaryService, false);

        when(fieldDefinition.getType()).thenReturn(dictionaryType);
        when(fieldDefinition.isRequired()).thenReturn(true);
        when(fieldDefinition.getDefaultValue()).thenReturn("asd");

        ComponentDefinition definition = new ComponentDefinition();
        definition.setName("selectComponent");
        definition.setViewDefinition(mock(InternalViewDefinition.class));
        SelectComponentPattern pattern = new SelectComponentPattern(definition);

        setField(pattern, "fieldDefinition", fieldDefinition);
        componentState = new SelectComponentState(pattern);
        setField(componentState, "locale", Locale.ENGLISH);
        setField(pattern, "defaultRequired", true);

    }

    private void stubValues(String... values) throws JSONException {
        Map<String, String> array = Maps.newLinkedHashMap();
        for (int i = 0; i < values.length; ++i) {
            array.put(values[i], values[i]);
        }
        when(dictionaryService.getValues(DICTIONARY_NAME, Locale.ENGLISH)).thenReturn(array);
    }

    @Test
    public void shouldNotAddAnotherValue() throws JSONException {
        // given

        stubValues("aaaa", "bbbb", "cccc", "dddd");
        componentState.setFieldValue("cccc");

        // when
        JSONObject content = componentState.renderContent();

        // then
        assertNotNull(content.getJSONArray(VALUES));
        assertEquals(4, content.getJSONArray(VALUES).length());
        assertEquals("aaaa", content.getJSONArray(VALUES).getJSONObject(0).getString(KEY));
        assertEquals("bbbb", content.getJSONArray(VALUES).getJSONObject(1).getString(KEY));
        assertEquals("cccc", content.getJSONArray(VALUES).getJSONObject(2).getString(KEY));
        assertEquals("dddd", content.getJSONArray(VALUES).getJSONObject(3).getString(KEY));
    }

    @Test
    public void shouldAddDeactivatedValueAtTheBeginning() throws JSONException {
        // given

        stubValues("bbbb", "cccc", "dddd", "eeee");
        componentState.setFieldValue("aaaa");

        // when
        JSONObject content = componentState.renderContent();

        // then
        assertNotNull(content.getJSONArray(VALUES));
        assertEquals(5, content.getJSONArray(VALUES).length());
        assertEquals("aaaa", content.getJSONArray(VALUES).getJSONObject(0).getString(KEY));
        assertEquals("bbbb", content.getJSONArray(VALUES).getJSONObject(1).getString(KEY));
        assertEquals("cccc", content.getJSONArray(VALUES).getJSONObject(2).getString(KEY));
        assertEquals("dddd", content.getJSONArray(VALUES).getJSONObject(3).getString(KEY));
        assertEquals("eeee", content.getJSONArray(VALUES).getJSONObject(4).getString(KEY));
    }

    @Test
    public void shouldAddDeactivatedValueToTheEnd() throws JSONException {
        // given

        stubValues("aaaa", "bbbb", "cccc", "dddd");
        componentState.setFieldValue("eeee");

        // when
        JSONObject content = componentState.renderContent();

        // then
        assertNotNull(content.getJSONArray(VALUES));
        assertEquals(5, content.getJSONArray(VALUES).length());
        assertEquals("aaaa", content.getJSONArray(VALUES).getJSONObject(0).getString(KEY));
        assertEquals("bbbb", content.getJSONArray(VALUES).getJSONObject(1).getString(KEY));
        assertEquals("cccc", content.getJSONArray(VALUES).getJSONObject(2).getString(KEY));
        assertEquals("dddd", content.getJSONArray(VALUES).getJSONObject(3).getString(KEY));
        assertEquals("eeee", content.getJSONArray(VALUES).getJSONObject(4).getString(KEY));
    }

    @Test
    public void shouldAddDeactivatedValueInTheMiddle() throws JSONException {
        // given

        stubValues("aaaa", "bbbb", "dddd", "eeee");
        componentState.setFieldValue("cccc");

        // when
        JSONObject content = componentState.renderContent();

        // then
        assertNotNull(content.getJSONArray("values"));
        assertEquals(5, content.getJSONArray("values").length());
        assertEquals("aaaa", content.getJSONArray(VALUES).getJSONObject(0).getString(KEY));
        assertEquals("bbbb", content.getJSONArray(VALUES).getJSONObject(1).getString(KEY));
        assertEquals("cccc", content.getJSONArray(VALUES).getJSONObject(2).getString(KEY));
        assertEquals("dddd", content.getJSONArray(VALUES).getJSONObject(3).getString(KEY));
        assertEquals("eeee", content.getJSONArray(VALUES).getJSONObject(4).getString(KEY));
    }
}
