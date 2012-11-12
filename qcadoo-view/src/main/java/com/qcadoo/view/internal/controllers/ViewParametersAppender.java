package com.qcadoo.view.internal.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewParametersAppender {

    @Value("${applicationDisplayName}")
    private String applicationDisplayName;

    @Value("${useCompressedStaticResources}")
    private boolean useCompressedStaticResources;

    public void appendCommonViewObjects(final ModelAndView mav) {
        mav.addObject("applicationDisplayName", applicationDisplayName);
        mav.addObject("useCompressedStaticResources", useCompressedStaticResources);
    }
}
