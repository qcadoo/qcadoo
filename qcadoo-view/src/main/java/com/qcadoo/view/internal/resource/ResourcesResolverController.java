package com.qcadoo.view.internal.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResourcesResolverController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/**/*", method = RequestMethod.GET)
    public void gerResource(final HttpServletRequest request, final HttpServletResponse response) {
        resourceService.serveResource(request, response);
    }

}
