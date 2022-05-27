package com.qcadoo.view.utils;

import com.qcadoo.view.internal.FileAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class FileAccessUtils {

    private static FileAccessUtils instance;

    @Autowired
    private FileAccessService fileAccessService;

    FileAccessUtils() {
        setInstance(this);
    }

    public static FileAccessUtils getInstance() {
        return instance;
    }

    private static void setInstance(final FileAccessUtils instance) {
        FileAccessUtils.instance = instance;
    }

    public FileAccessService getFileAccessService() {
        return fileAccessService;
    }

}
