package com.qcadoo.view.internal.components.file;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qcadoo.view.api.FileService;

@Service
public class FileUtils {

    @Autowired
    private FileService fileService;

    private static FileUtils instance;

    @PostConstruct
    public void init() {
        initialise(this);
    }

    private static void initialise(final FileUtils fileUtils) {
        FileUtils.instance = fileUtils;
    }

    public static String getName(final String path) {
        return FileUtils.instance.fileService.getName(path);
    }

    public static String getLastModificationDate(final String path) {
        return FileUtils.instance.fileService.getLastModificationDate(path);
    }

    public static String getUrl(final String path) {
        return FileUtils.instance.fileService.getUrl(path);
    }

    public static String getPathFromUrl(final String url) {
        return FileUtils.instance.fileService.getPathFromUrl(url);
    }

    public static InputStream getInputStream(final String path) {
        return FileUtils.instance.fileService.getInputStream(path);
    }

    public static String upload(final MultipartFile multipartFile) throws IOException {
        return FileUtils.instance.fileService.upload(multipartFile);
    }

    public static String getContentType(final String path) {
        return FileUtils.instance.fileService.getContentType(path);
    }

}
