/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.3
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.view.internal.controllers;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.PasswordResetTokenService;
import com.qcadoo.view.internal.LogoComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public final class PasswordChangeController {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private ViewParametersAppender viewParametersAppender;

    @Autowired
    private LogoComponent logoComponent;

    @RequestMapping(value = "passwordChange", method = RequestMethod.GET, params = "token")
    public ModelAndView getResetPasswordFormView(final Locale locale, @RequestParam final String token) {

        ModelAndView mav = new ModelAndView();

        viewParametersAppender.appendCommonViewObjects(mav);

        mav.setViewName("qcadooView/passwordChange");

        mav.addObject("translation", translationService.getMessagesGroup("security", locale));
        mav.addObject("currentLanguage", locale.getLanguage());
        mav.addObject("locales", translationService.getLocales());
        mav.addObject("logoPath", logoComponent.prepareDefaultLogoPath());

        mav.addObject("token", token);

        return mav;
    }

    @RequestMapping(value = "passwordChange", method = RequestMethod.POST, params = "token")
    @ResponseBody
    public PasswordChangeResponseBody processResetPasswordFormView(@RequestParam final String token, @RequestParam final String password,
            @RequestParam final String passwordConfirmation) {
        if (StringUtils.isBlank(password)) {
            return respondWithError("security.userChangePassword.blankPassword");
        }
        try {
            Entity user = passwordResetTokenService.processPasswordChange(token, password, passwordConfirmation);

            return respondFor(user);
        } catch (IllegalArgumentException iae) {
            return respondWithError("security.userChangePassword.invalidToken");
        }
    }

    private PasswordChangeResponseBody respondFor(final Entity user) {
        if (user.isValid()) {
            return respondOK();
        } else {
            if (user.getErrors().values().size() > 0) {
                return respondWithError(user.getErrors().values().iterator().next().getMessage());
            } else {
                return respondWithError("security.message.errorContent");
            }
        }
    }

    private PasswordChangeResponseBody respondOK() {
        return new PasswordChangeResponseBody("ok", StringUtils.EMPTY);
    }

    private PasswordChangeResponseBody respondWithError(final String code) {
        return new PasswordChangeResponseBody("error", translationService.translate(code, LocaleContextHolder.getLocale()));
    }

    public static class PasswordChangeResponseBody {

        private final String status;

        private final String errorMessage;

        PasswordChangeResponseBody(final String status, final String errorMessages) {
            this.status = status;
            this.errorMessage = errorMessages;
        }

        public String getStatus() {
            return status;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }

}
