package com.qcadoo.view.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String getName(final String path);

    String getLastModificationDate(final String path);

    String getUrl(final String path);

    String getPathFromUrl(final String url);

    InputStream getInputStream(final String path);

    String upload(final MultipartFile multipartFile) throws IOException;

    String getContentType(final String path);

    File create(String string);

}