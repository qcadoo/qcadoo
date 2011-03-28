package com.qcadoo.view.internal.crud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ResourcesResolverController {

    @Autowired
    private ResourceService resourceService;

    // @RequestMapping(value = "/**/*", method = RequestMethod.GET)
    public void gerResource(HttpServletRequest request, HttpServletResponse response) {

        resourceService.serveResource(request, response);
    }

}
