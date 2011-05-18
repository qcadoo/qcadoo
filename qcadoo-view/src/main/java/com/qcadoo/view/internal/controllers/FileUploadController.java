/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
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

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.view.internal.components.file.FileUtils;

@Controller
public class FileUploadController {

    @Autowired
    private TranslationService translationService;

    @RequestMapping(value = "fileUpload", method = RequestMethod.GET)
    public ModelAndView upload(final Locale locale) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("qcadooView/fileUpload");
        mav.addObject("translation", translationService.getMessagesGroup("fileUpload", locale));
        mav.addObject("fileLastModificationDate", "");
        mav.addObject("fileUrl", "");
        mav.addObject("fileName", "");
        mav.addObject("filePath", "");
        mav.addObject("fileUploadError", "");
        return mav;
    }

    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("Filedata") final MultipartFile file, final Locale locale) {
        String error = null;
        String path = null;

        try {
            path = FileUtils.upload(file);
        } catch (IOException e) {
            error = e.getMessage();
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("qcadooView/fileUpload");
        mav.addObject("translation", translationService.getMessagesGroup("fileUpload", locale));

        if (path != null) {
            mav.addObject("fileLastModificationDate", FileUtils.getLastModificationDate(path));
            mav.addObject("fileUrl", FileUtils.getUrl(path));
            mav.addObject("fileName", FileUtils.getName(path));
            mav.addObject("filePath", path);
        } else {
            mav.addObject("fileLastModificationDate", "");
            mav.addObject("fileUrl", "");
            mav.addObject("fileName", "");
            mav.addObject("filePath", "");
        }

        mav.addObject("fileUploadError", error);
        return mav;
    }
}
