/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.8
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
package com.qcadoo.model.internal.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.model.api.file.FileService;
import com.qcadoo.tenant.api.MultiTenantUtil;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private static FileService instance;

    private final String fileUrlPrefix = "/files/";

    private File uploadDirectory;

    public FileServiceImpl() {
        FileServiceImpl.setInstance(this);
    }

    @Value("${reportPath}")
    public void setUploadDirectory(final String uploadDirectory) {
        this.uploadDirectory = new File(uploadDirectory);
    }

    @Override
    public String getName(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        return path.substring(path.lastIndexOf(File.separatorChar) + 15);
    }

    @Override
    public String getLastModificationDate(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        Date date = new Date(Long.valueOf(path.substring(path.lastIndexOf(File.separatorChar) + 1,
                path.lastIndexOf(File.separatorChar) + 14)));
        return new SimpleDateFormat(DateUtils.DATE_FORMAT).format(date);
    }

    @Override
    public String getUrl(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        return fileUrlPrefix + normalizeSeparators(path.substring(uploadDirectory.getAbsolutePath().length() + 1));
    }

    private String normalizeSeparators(final String string) {
        if ("\\".equals(File.separator)) {
            return string.replaceAll("\\\\", "/");
        } else {
            return string;
        }
    }

    private String denormalizeSeparators(final String string) {
        if ("\\".equals(File.separator)) {
            return string.replaceAll("/", "\\\\");
        } else {
            return string;
        }
    }

    @Override
    public String getPathFromUrl(final String url) {
        String denormalizedUrl = denormalizeSeparators(url);
        return uploadDirectory.getAbsolutePath() + File.separator
                + denormalizedUrl.substring(denormalizedUrl.indexOf(File.separatorChar) + fileUrlPrefix.length() - 1);
    }

    @Override
    public InputStream getInputStream(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String upload(final MultipartFile multipartFile) throws IOException {
        File file = getFileFromFilename(multipartFile.getOriginalFilename());

        OutputStream output = null;

        try {
            output = new FileOutputStream(file);
            IOUtils.copy(multipartFile.getInputStream(), output);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            IOUtils.closeQuietly(output);
            throw e;
        }

        return file.getAbsolutePath();
    }

    @Override
    public String create(final String filename) {
        return getFileFromFilename(filename).getAbsolutePath();
    }

    private File getFileFromFilename(final String filename) {
        String date = Long.toString(System.currentTimeMillis());
        File directory = new File(uploadDirectory, MultiTenantUtil.getCurrentTenantId() + File.separator
                + date.charAt(date.length() - 1) + File.separator + date.charAt(date.length() - 2) + File.separator);
        directory.mkdirs();
        return new File(directory, date + "_" + getNormalizedFileName(filename));
    }

    private String getNormalizedFileName(final String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.]+", "_");
    }

    @Override
    public String getContentType(final String path) {
        return new MimetypesFileTypeMap().getContentType(new File(path));
    }

    @Override
    public void remove(final String path) {
        FileUtils.deleteQuietly(new File(path));
    }

    public static FileService getInstance() {
        return instance;
    }

    private static void setInstance(final FileService instance) {
        FileServiceImpl.instance = instance;
    }

}
