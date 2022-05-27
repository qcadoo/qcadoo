package com.qcadoo.security.internal.matchers;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class CustomCsrfRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^GET$");

    private AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/integration/rest/**"),
            new AntPathRequestMatcher("/rest/warehouse/**"),
            new AntPathRequestMatcher("/rest/product/**"),
            new AntPathRequestMatcher("/rest/order/**"),
            new AntPathRequestMatcher("/rest/masterorder/**"),
            new AntPathRequestMatcher("/rest/delivery/**"),
            new AntPathRequestMatcher("/rest/document/**"),
            new AntPathRequestMatcher("/rest/pipedrive/**"),
            new AntPathRequestMatcher("/rest/sgOrdsGantt/**"),
            new AntPathRequestMatcher("/rest/sgOrdersGantt/**"),
            new AntPathRequestMatcher("/rest/operTasksGantt/**"),
            new AntPathRequestMatcher("/rest/operationalTasksGantt/**"),
            new AntPathRequestMatcher("/rest/cmms/**")
    };

    @Override
    public boolean matches(final HttpServletRequest request) {
        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }

        for (AntPathRequestMatcher requestMatcher : requestMatchers) {
            if (requestMatcher.matches(request)) {
                return false;
            }
        }

        return true;
    }

}
