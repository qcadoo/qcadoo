package com.qcadoo.view.internal.controllers;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.file.FileService;
import com.qcadoo.view.internal.FileAccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
@RequestMapping(FileAccessController.URL_PREFIX)
public final class FileAccessController {

    public static final String URL_PREFIX = "/secured";

    private static final String L_FILES = "/files";

    private static final String L_FILE_ACCESS_ERROR_HEADER = "qcadooView.errorPage.error.fileAccessError.header";

    private static final String L_FILE_ACCESS_ERROR_EXPLANATION = "qcadooView.errorPage.error.fileAccessError.explanation";

    private static final String L_FILE_NOT_EXISTS_ERROR_HEADER = "qcadooView.errorPage.error.fileNotExistsError.header";

    private static final String L_FILE_NOT_EXISTS_ERROR_EXPLANATION = "qcadooView.errorPage.error.fileNotExistsError.explanation";

    @Autowired
    private TranslationService translationService;

    @Autowired
    private FileAccessService fileAccessService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ViewParametersAppender viewParametersAppender;

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public ModelAndView consumeToken(@PathVariable("token") final String token, final ModelAndView modelAndView, final Locale locale) {
        String url = fileAccessService.consumeTokenAndGetOriginalUrl(token);

        if (isNotBlank(url)) {
            if (fileExists(url)) {
                modelAndView.setViewName("forward:" + url);
            } else {
                setFileAccessErrorPage(modelAndView, locale, L_FILE_NOT_EXISTS_ERROR_HEADER, L_FILE_NOT_EXISTS_ERROR_EXPLANATION);
            }
        } else {
            setFileAccessErrorPage(modelAndView, locale, L_FILE_ACCESS_ERROR_HEADER, L_FILE_ACCESS_ERROR_EXPLANATION);
        }

        return modelAndView;
    }

    private boolean fileExists(final String url) {
        String path = url;

        if (StringUtils.startsWith(url, L_FILES)) {
            path = fileService.getPathFromUrl(url);
        }

        File file = new File(path);

        return (file.exists() && file.canRead());
    }

    private void setFileAccessErrorPage(final ModelAndView modelAndView, final Locale locale, final String errorHeader, final String errorExplanation) {
        viewParametersAppender.appendCommonViewObjects(modelAndView);

        modelAndView.addObject("errorHeader", translationService.translate(errorHeader, locale));
        modelAndView.addObject("errorExplanation", translationService.translate(errorExplanation, locale));

        modelAndView.setViewName("qcadooView/fileAccessError");
    }

}
