/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.mail.api.InvalidMailAddressException;
import com.qcadoo.security.api.PasswordReminderService;

@Controller
public final class PasswordResetController {

    private static final String FALSE = "false";

    @Autowired
    private TranslationService translationService;

    @Autowired
    private PasswordReminderService passwordReminderService;

    @Value("${setAsDemoEnviroment}")
    private boolean setAsDemoEnviroment;

    @Value("${useCompressedStaticResources}")
    private boolean useCompressedStaticResources;

    @RequestMapping(value = "passwordReset", method = RequestMethod.GET)
    public ModelAndView getForgotPasswordFormView(@RequestParam(required = false, defaultValue = FALSE) final Boolean iframe,
            @RequestParam(required = false, defaultValue = FALSE) final Boolean popup, final Locale locale) {

        if (setAsDemoEnviroment) {
            return new ModelAndView(new RedirectView("main.html"));
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("qcadooView/passwordReset");
        mav.addObject("translation", translationService.getMessagesGroup("security", locale));
        mav.addObject("currentLanguage", locale.getLanguage());
        mav.addObject("locales", translationService.getLocales());

        mav.addObject("iframe", iframe);
        mav.addObject("popup", popup);

        mav.addObject("useCompressedStaticResources", useCompressedStaticResources);

        return mav;
    }

    @RequestMapping(value = "passwordReset", method = RequestMethod.POST)
    public @ResponseBody
    String processForgotPasswordFormView(@RequestParam final String login) {
        if (setAsDemoEnviroment) {
            return null;
        }
        if (StringUtils.isBlank(login)) {
            return "loginIsBlank";
        }
        return performPasswordReseting(login);
    }

    private String performPasswordReseting(final String login) {
        try {
            passwordReminderService.generateAndSendNewPassword(login);
        } catch (UsernameNotFoundException e) {
            return "userNotFound";
        } catch (InvalidMailAddressException e) {
            return "invalidMailAddress";
        } catch (MailException e) {
            return "invalidMailConfig";
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

}
