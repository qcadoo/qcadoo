package com.qcadoo.model.api.file;

import com.qcadoo.model.internal.file.FileServiceImpl;

/**
 * Helper for getting the {@link FileService} instance.
 * 
 * @since 0.4.1
 */
public class FileUtils {

    /**
     * Returns the {@link FileService} instance.
     * 
     * @return file service
     */
    public static FileService getInstance() {
        return FileServiceImpl.getInstance();
    }

}
