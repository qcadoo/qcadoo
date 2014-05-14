/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.model.types;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DictionaryService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.EnumeratedType;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.api.types.ManyToManyType;
import com.qcadoo.model.internal.DataAccessTest;
import com.qcadoo.model.internal.DefaultEntity;
import com.qcadoo.model.internal.FieldDefinitionImpl;
import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.types.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FieldTypeFactoryTest extends DataAccessTest {

    private final FieldDefinition fieldDefinition = new FieldDefinitionImpl(null, "aa");

    @Test
    public void shouldReturnEnumType() throws Exception {
        // given
        TranslationService translationService = mock(TranslationService.class);
        given(translationService.translate("path.value.val1", Locale.ENGLISH)).willReturn("i18nVal1");
        given(translationService.translate("path.value.val2", Locale.ENGLISH)).willReturn("i18nVal2");
        given(translationService.translate("path.value.val3", Locale.ENGLISH)).willReturn("i18nVal3");

        // when
        EnumeratedType fieldType = new EnumType(translationService, "path", true, "val1", "val2", "val3");

        // then
        assertThat(fieldType, is(EnumType.class));

        assertThat(fieldType.values(Locale.ENGLISH).keySet(), hasItems("val1", "val2", "val3"));
        assertThat(fieldType.values(Locale.ENGLISH).values(), hasItems("i18nVal1", "i18nVal2", "i18nVal3"));
        assertEquals(String.class, fieldType.getType());

        ValueAndError valueAndError1 = fieldType.toObject(fieldDefinition, "val1");
        ValueAndError valueAndError2 = fieldType.toObject(fieldDefinition, "val4");

        assertTrue(valueAndError1.isValid());
        assertFalse(valueAndError2.isValid());
        assertNotNull(valueAndError1.getValue());
        assertNull(valueAndError2.getValue());
        assertEquals("qcadooView.validate.field.error.invalidDictionaryItem", valueAndError2.getMessage());
        assertEquals("[val1, val2, val3]", valueAndError2.getArgs()[0]);
    }

    @Test
    public void shouldReturnDictionaryType() throws Exception {
        // given
        DictionaryService dictionaryService = mock(DictionaryService.class);
        given(dictionaryService.getValues("dictionary", Locale.ENGLISH)).willReturn(
                ImmutableMap.of("val1", "val1", "val2", "val2", "val3", "val3"));
        given(dictionaryService.getKeys("dictionary")).willReturn(Lists.newArrayList("val1", "val2", "val3"));

        // when
        EnumeratedType fieldType = new DictionaryType("dictionary", dictionaryService, true);

        // then
        assertThat(fieldType, is(DictionaryType.class));
        assertThat(fieldType.values(Locale.ENGLISH).keySet(), hasItems("val1", "val2", "val3"));
        assertThat(fieldType.values(Locale.ENGLISH).values(), hasItems("val1", "val2", "val3"));
        assertEquals(String.class, fieldType.getType());

        ValueAndError valueAndError1 = fieldType.toObject(fieldDefinition, "val1");
        ValueAndError valueAndError2 = fieldType.toObject(fieldDefinition, "val4");
        assertNotNull(valueAndError1.getValue());
        assertNull(valueAndError2.getValue());
        assertEquals("qcadooView.validate.field.error.invalidDictionaryItem", valueAndError2.getMessage());
        assertEquals("[val1, val2, val3]", valueAndError2.getArgs()[0]);
    }

    @Test
    public void shouldReturnBooleanType() throws Exception {
        // when
        FieldType fieldType = new BooleanType();

        // then
        assertThat(fieldType, is(BooleanType.class));
        assertEquals(Boolean.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, false).isValid());
    }

    @Test
    public void shouldReturnDateType() throws Exception {
        // when
        FieldType fieldType = new DateType();

        // then
        assertThat(fieldType, is(DateType.class));
        assertEquals(Date.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, new Date()).isValid());
    }

    @Test
    public void shouldReturnDateTimeType() throws Exception {
        // when
        FieldType fieldType = new DateTimeType();

        // then
        assertThat(fieldType, is(DateTimeType.class));
        assertEquals(Date.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, new Date()).isValid());
    }

    @Test
    public void shouldReturnDecimalType() throws Exception {
        // when
        FieldType fieldType = new DecimalType();

        // then
        assertThat(fieldType, is(DecimalType.class));
        assertEquals(BigDecimal.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, BigDecimal.valueOf(1.21)).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, BigDecimal.valueOf(1)).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, BigDecimal.valueOf(1)).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, BigDecimal.valueOf(1234567)).isValid());
    }

    @Test
    public void shouldReturnIntegerType() throws Exception {
        // when
        FieldType fieldType = new IntegerType();

        // then
        assertThat(fieldType, is(IntegerType.class));
        assertEquals(Integer.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, 1).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, 1234567890).isValid());
    }

    @Test
    public void shouldReturnStringType() throws Exception {
        // when
        FieldType fieldType = new StringType();

        // then
        assertThat(fieldType, is(StringType.class));
        assertEquals(String.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, "test").isValid());
        assertTrue(fieldType.toObject(fieldDefinition, StringUtils.repeat("a", 255)).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, StringUtils.repeat("a", 300)).isValid());
    }

    @Test
    public void shouldReturnTextType() throws Exception {
        // when
        FieldType fieldType = new TextType();

        // then
        assertThat(fieldType, is(TextType.class));
        assertEquals(String.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, "test").isValid());
        assertTrue(fieldType.toObject(fieldDefinition, StringUtils.repeat("a", 2048)).isValid());
        assertTrue(fieldType.toObject(fieldDefinition, StringUtils.repeat("a", 2049)).isValid());
    }

    @Test
    public void shouldReturnBelongToType() throws Exception {
        // when
        FieldType fieldType = new BelongsToEntityType("parent", "entity", dataDefinitionService, false, true);

        // then
        assertThat(fieldType, is(BelongsToEntityType.class));
        assertEquals(Object.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, new DefaultEntity(dataDefinition)).isValid());
    }

    @Test
    public void shouldReturnManyToManyType() throws Exception {
        // when
        FieldType fieldType = new ManyToManyEntitiesType("parent", "entity", "joinFieldName", ManyToManyType.Cascade.NULLIFY,
                true, dataDefinitionService);

        // then
        assertThat(fieldType, is(ManyToManyEntitiesType.class));
        assertEquals(Set.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, new DefaultEntity(dataDefinition)).isValid());
    }

    @Test
    public void shouldReturnPasswordType() throws Exception {
        // when
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        FieldType fieldType = new PasswordType(passwordEncoder);

        // then
        assertThat(fieldType, is(PasswordType.class));
        assertEquals(String.class, fieldType.getType());
    }

    @Test
    public void shouldReturnPriorityType() throws Exception {
        // given
        FieldDefinition fieldDefinition = new FieldDefinitionImpl(null, "aaa");

        // when
        FieldType fieldType = new PriorityType(fieldDefinition);

        // then
        assertThat(fieldType, is(PriorityType.class));
        assertEquals(Integer.class, fieldType.getType());
        assertTrue(fieldType.toObject(fieldDefinition, 1).isValid());
        assertEquals(fieldDefinition, ((PriorityType) fieldType).getScopeFieldDefinition());
    }
}
