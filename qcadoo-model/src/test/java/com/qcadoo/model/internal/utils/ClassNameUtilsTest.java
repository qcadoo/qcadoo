package com.qcadoo.model.internal.utils;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class ClassNameUtilsTest {

    @Test
    public void shouldReturnCorrectClassName() throws Exception {
        // when
        String className = ClassNameUtils.getFullyQualifiedClassName("plugin", "model");
        String camelClassName = ClassNameUtils.getFullyQualifiedClassName("pluginName", "modelName");

        //then
        assertEquals("com.qcadoo.model.beans.plugin.PluginModel", className);
        assertEquals("com.qcadoo.model.beans.pluginName.PluginNameModelName", camelClassName);
    }
}
