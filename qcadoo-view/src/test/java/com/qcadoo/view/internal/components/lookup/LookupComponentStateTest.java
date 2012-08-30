package com.qcadoo.view.internal.components.lookup;

import static org.mockito.Mockito.when;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.ExpressionService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.ExpressionServiceImpl;

public class LookupComponentStateTest {

    private LookupComponentState lookup;

    @Mock
    private FieldDefinition scopeField;

    @Mock
    private Entity entity;

    @Mock
    private DataDefinition dataDefinition;

    @Mock
    private LookupComponentPattern componentPattern;

    @Mock
    private ExpressionService expressionService;

    @Mock
    private JSONObject json;

    ExpressionServiceImpl esi;

    @Before
    public void init() throws JSONException {
        MockitoAnnotations.initMocks(this);
        when(componentPattern.isPersistent()).thenReturn(true);
        lookup = new LookupComponentState(scopeField, "fieldCode", "expression", componentPattern);
        lookup.initialize(json, Locale.ENGLISH);
        lookup.setDataDefinition(dataDefinition);
        esi = new ExpressionServiceImpl();
        ReflectionTestUtils.invokeMethod(esi, "initialise", expressionService);
    }

    @After
    public void after() throws JSONException {
        ReflectionTestUtils.invokeMethod(esi, "initialise", new ExpressionServiceImpl());
    }

    @Test
    public void shouldReturnNullWhenValueIsNull() throws Exception {
        lookup.setFieldValue(null);
        // when
        Entity result = lookup.getEntity();
        // then
        Assert.assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenValueIsIncorrect() throws Exception {
        lookup.setFieldValue("");
        // given
        Entity result = lookup.getEntity();
        // then
        Assert.assertNull(result);
    }

    @Test
    public void shouldReturnEntityWhenIdIsString() throws Exception {
        // given
        String id = "1";
        when(dataDefinition.get(Long.valueOf(id))).thenReturn(entity);
        lookup.setFieldValue(id);
        // when
        Entity result = lookup.getEntity();
        // then
        Assert.assertEquals(entity, result);
    }

    @Test
    public void shouldReturnNullWhenEntityDoesnotExistsInDB() throws Exception {
        // given
        Long id = 1L;
        lookup.setFieldValue(id);
        when(dataDefinition.get(id)).thenReturn(null);
        // when
        Entity result = lookup.getEntity();
        // then
        Assert.assertNull(result);
    }

    @Test
    public void shouldReturnEntity() throws Exception {
        // given
        Long id = 1L;
        when(dataDefinition.get(id)).thenReturn(entity);
        lookup.setFieldValue(id);
        // when
        Entity result = lookup.getEntity();
        // then
        Assert.assertEquals(entity, result);
    }
}
