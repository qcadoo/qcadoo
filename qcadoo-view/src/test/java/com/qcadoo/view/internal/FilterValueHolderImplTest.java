package com.qcadoo.view.internal;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class FilterValueHolderImplTest {

    private FilterValueHolderImpl filterValueHolder;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        filterValueHolder = new FilterValueHolderImpl();
    }

    @Test
    public final void shouldClearContentsWithoutException() {
        // given
        filterValueHolder.put("someBoolean", true);
        filterValueHolder.put("someDecimal", BigDecimal.ONE);
        filterValueHolder.put("someString", "stringValue");

        // when & then
        try {
            // I know that this is ugly..
            ReflectionTestUtils.invokeMethod(filterValueHolder, "clearHolder");
        } catch (ConcurrentModificationException cme) {
            Assert.fail();
        }
    }

}
