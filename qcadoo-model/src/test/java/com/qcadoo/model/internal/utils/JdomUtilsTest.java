package com.qcadoo.model.internal.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jdom.Element;
import org.jdom.Namespace;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;


public class JdomUtilsTest {

    private Element element;
    
    @Before
    public void init() {
        element = mock(Element.class);
    }
    
    @Test
    public void shouldRenameNamespace() throws Exception {
        // given
        Element childElement = mock(Element.class);
        when(element.getChildren()).thenReturn(Lists.newArrayList(childElement, childElement));
        
        // when
        JdomUtils.replaceNamespace(element, Mockito.any(Namespace.class));
        
        //then
        verify(element).setNamespace(Mockito.any(Namespace.class));
        verify(childElement, times(2)).setNamespace(Mockito.any(Namespace.class));
    }
}
