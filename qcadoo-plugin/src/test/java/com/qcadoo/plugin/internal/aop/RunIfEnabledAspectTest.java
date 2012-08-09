package com.qcadoo.plugin.internal.aop;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.qcadoo.plugin.api.RunIfEnabled;

public class RunIfEnabledAspectTest {

    @Test
    public final void checkPointcutDeclarations() {
        assertEquals("com.qcadoo.plugin.api.RunIfEnabled", RunIfEnabled.class.getCanonicalName());
    }
}
