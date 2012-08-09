package com.qcadoo.security.internal.aop;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.qcadoo.model.api.aop.Auditable;

public class AuditableAdviceTest {

    @Test
    public final void checkPointcutDefinifions() {
        assertEquals("com.qcadoo.model.api.aop.Auditable", Auditable.class.getCanonicalName());
    }
}
