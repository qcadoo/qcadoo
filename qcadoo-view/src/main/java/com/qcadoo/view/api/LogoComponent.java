package com.qcadoo.view.api;

import com.qcadoo.view.constants.ApplicationProfile;
import com.qcadoo.view.utils.ViewParametersAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.qcadoo.view.constants.ApplicationProfile.*;

@Component
public class LogoComponent {

    private final static String L_DEFAULT_LOGO_BASE_PATH = "/qcadooView/public/css/core/images/login/new/";

    private final static String L_MENU_LOGO_BASE_PATH = "/qcadooView/public/css/core/menu/images-new/";

    private ViewParametersAppender viewParametersAppender;

    @Autowired
    public LogoComponent(ViewParametersAppender viewParametersAppender) {
        this.viewParametersAppender = viewParametersAppender;
    }

    public String prepareDefaultLogoPath() {
        StringBuilder logoBuilder = new StringBuilder(L_DEFAULT_LOGO_BASE_PATH);

        if (parseString(viewParametersAppender.getApplicationProfile()) == TEST) {
            logoBuilder.append(TEST.getLogo());
        } else {
            logoBuilder.append(MES.getLogo());
        }

        logoBuilder.append(".png");
        return logoBuilder.toString();
    }

    public String prepareMenuLogoPath(boolean white) {
        StringBuilder logoBuilder = new StringBuilder(LogoComponent.L_MENU_LOGO_BASE_PATH);

        switch (ApplicationProfile.parseString(viewParametersAppender.getApplicationProfile())) {
            case MES:
                logoBuilder.append(MES.getLogo());
                break;
            case APS:
                logoBuilder.append(APS.getLogo());
                break;
            case MES_APS:
                logoBuilder.append(MES_APS.getLogo());
                break;
            case WMS:
                logoBuilder.append(WMS.getLogo());
                break;
            default:
                logoBuilder.append(TEST.getLogo());
        }

        if (white) {
            logoBuilder.append("-white");
        }
        logoBuilder.append(".png");
        return logoBuilder.toString();
    }

}
