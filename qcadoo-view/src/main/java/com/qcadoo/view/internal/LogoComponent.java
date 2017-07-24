package com.qcadoo.view.internal;

import com.qcadoo.view.internal.controllers.ViewParametersAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogoComponent {

    private final static String L_LOGIN_PAGE_BASE_PATH = "/qcadooView/public/css/core/images/login/";
    private final static String L_MENU_BASE_PATH = "/qcadooView/public/css/core/menu/images-new/";
    private final static String L_LOGO_NAME = "qcadoo-logo.png";
    private final static String L_TEST_LOGO_NAME = "qcadoo-test-logo.png";
    private final static String L_TEST_APPLICATION_PROFILE = "TEST";

    private ViewParametersAppender viewParametersAppender;

    @Autowired
    public LogoComponent(ViewParametersAppender viewParametersAppender) {
        this.viewParametersAppender = viewParametersAppender;
    }

    public String prepareMenuLogoPath() {
        return prepare(L_MENU_BASE_PATH);
    }

    public String prepareLoginPageLogoPath() {
        return prepare(L_LOGIN_PAGE_BASE_PATH);
    }

    private String prepare(String path) {
        StringBuilder logoBuilder = new StringBuilder(path);
        if(L_TEST_APPLICATION_PROFILE.equals(viewParametersAppender.getApplicationProfile())) {
            logoBuilder.append(L_TEST_LOGO_NAME);
        } else {
            logoBuilder.append(L_LOGO_NAME);
        }
        return logoBuilder.toString();
    }

}
