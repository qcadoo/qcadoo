package com.qcadoo.view.internal.components.grid;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.ImmutableMap;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchRestrictions.SearchMatchMode;
import com.qcadoo.model.api.types.FieldType;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SearchRestrictions.class)
public class GridComponentFilterUtilsTest {

    private static final String TEST_COL = "testCol";

    private static final String TEST_FIELD = "testField";

    @Mock
    private DataDefinition dataDefinition;

    @Mock
    private SearchCriteriaBuilder criteria;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SearchRestrictions.class);
    }

    @Test
    public final void shouldFilterColumnWithIntegerValues() throws GridComponentFilterException {
        // when
        performFiltering("3", Integer.class);

        // then
        PowerMockito.verifyStatic();
        SearchRestrictions.eq(TEST_FIELD, 3);

        PowerMockito.verifyStatic(never());
        SearchRestrictions.eq(TEST_FIELD, "3");

        Mockito.verify(criteria).add(SearchRestrictions.eq(TEST_FIELD, 3));
    }

    @Test
    public final void shouldNotFilterColumnWithIntegerValuesIfFilterIsBlank() throws GridComponentFilterException {
        // when
        performFiltering(" ", Integer.class);

        // then
        verify(criteria, never()).add(SearchRestrictions.eq(Mockito.eq(TEST_FIELD), Mockito.any()));
    }

    @Test
    public final void shouldThrowExceptionForIncorenctFilterValueForIntegerColumn() throws GridComponentFilterException {
        try {
            performFiltering("aaa", Integer.class);
            Assert.fail();
        } catch (GridComponentFilterException gcfe) {
            // success
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldFilterColumnWithBigDecimalValues() throws GridComponentFilterException {
        // when
        performFiltering("3.14", BigDecimal.class);

        // then
        PowerMockito.verifyStatic();
        SearchRestrictions.eq(TEST_FIELD, new BigDecimal("3.14"));

        PowerMockito.verifyStatic(never());
        SearchRestrictions.eq(TEST_FIELD, "3.14");

        verify(criteria).add(SearchRestrictions.eq(TEST_FIELD, new BigDecimal("3.14")));
    }

    @Test
    public final void shouldNotFilterColumnWithBigDecimalValuesIfFilterIsBlank() throws GridComponentFilterException {
        // when
        performFiltering(" ", BigDecimal.class);

        // then
        verify(criteria, never()).add(SearchRestrictions.eq(Mockito.eq(TEST_FIELD), Mockito.any()));
    }

    @Test
    public final void shouldThrowExceptionForIncorenctFilterValueForBigDecimalColumn() throws GridComponentFilterException {
        try {
            performFiltering("aaa", BigDecimal.class);
            Assert.fail();
        } catch (GridComponentFilterException gcfe) {
            // success
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public final void shouldFilterColumnWithStringValues() throws GridComponentFilterException {
        // when
        performFiltering("someValue", String.class);

        // then
        PowerMockito.verifyStatic();
        SearchRestrictions.like(TEST_FIELD, "someValue", SearchMatchMode.ANYWHERE);

        verify(criteria).add(SearchRestrictions.like(TEST_FIELD, "someValue", SearchMatchMode.ANYWHERE));
    }

    @Test
    public final void shouldNotFilterColumnWithStringValuesIfFilterIsBlank() throws GridComponentFilterException {
        // when
        performFiltering(" ", String.class);

        // then
        verify(criteria, never()).add(
                SearchRestrictions.like(Mockito.eq(TEST_FIELD), Mockito.anyString(), Mockito.any(SearchMatchMode.class)));
    }

    private void performFiltering(final String filterValue, final Class<?> clazz) throws GridComponentFilterException {
        final Map<String, String> filters = ImmutableMap.of(TEST_COL, filterValue);
        final FieldDefinition fieldDefinition = mockFieldDefinition(TEST_FIELD, clazz);
        final Map<String, GridComponentColumn> columns = ImmutableMap.of(TEST_COL,
                buildGridComponentColumn(TEST_COL, fieldDefinition));

        GridComponentFilterUtils.addFilters(filters, columns, dataDefinition, criteria);
    }

    @SuppressWarnings("unchecked")
    private FieldDefinition mockFieldDefinition(final String fieldName, @SuppressWarnings("rawtypes") final Class typeClass) {
        final FieldDefinition fieldDefinition = mock(FieldDefinition.class);
        given(fieldDefinition.getName()).willReturn(fieldName);

        final FieldType fieldType = mock(FieldType.class);
        given(fieldType.getType()).willReturn(typeClass);
        given(fieldDefinition.getType()).willReturn(fieldType);

        given(dataDefinition.getField(fieldName)).willReturn(fieldDefinition);
        return fieldDefinition;
    }

    private GridComponentColumn buildGridComponentColumn(final String name, final FieldDefinition fieldDefinition) {
        final GridComponentColumn gridComponentColumn = new GridComponentColumn(name);
        gridComponentColumn.addField(fieldDefinition);
        return gridComponentColumn;

    }

}
