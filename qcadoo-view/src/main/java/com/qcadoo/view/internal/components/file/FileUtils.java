package com.qcadoo.view.internal.components.file;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static String getName(final String path) {
        return "filename.pdf";
    }

    public static String getLastModificationDate(final String path) {
        return "2010-04-01";
    }

    public static String getUrl(final String path) {
        return "http://127.0.0.1:8080/files/1/2/342_filename_2010-04-01.pdf";
    }

    public static String upload(final MultipartFile file) throws IOException {
        return "/tmp/1/2/3/432_Fwefwefew.pdf";
    }

}
