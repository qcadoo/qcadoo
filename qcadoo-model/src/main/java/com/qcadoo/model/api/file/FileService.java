package com.qcadoo.model.api.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service for managing files.
 * 
 * @since 0.4.1
 */
public interface FileService {

    /**
     * Returns name of the file from given path.
     * 
     * @param path
     *            path
     * @return name
     */
    String getName(final String path);

    /**
     * Returns last modification date of the file from given path.
     * 
     * @param path
     *            path
     * @return last modification date
     */
    String getLastModificationDate(final String path);

    /**
     * Returns URL for the file from given path.
     * 
     * @param path
     *            path
     * @return URL
     */
    String getUrl(final String path);

    String getPathFromUrl(final String url);

    /**
     * Returns stream of the file from given path.
     * 
     * @param path
     *            path
     * @return stream
     */
    InputStream getInputStream(final String path);

    /**
     * Create file from given uploaded file.
     * 
     * @param multipartFile
     *            uploaded file
     * @return path
     */
    String upload(final MultipartFile multipartFile) throws IOException;

    /**
     * Returns content type of the file from given path.
     * 
     * @param path
     *            path
     * @return content type
     */
    String getContentType(final String path);

    /**
     * Create empty file with given name.
     * 
     * @param filename
     *            filename
     * @return path
     */
    String create(String filename);

    /**
     * Remove the file from given path.
     * 
     * @param path
     *            path
     */
    void remove(String path);

}