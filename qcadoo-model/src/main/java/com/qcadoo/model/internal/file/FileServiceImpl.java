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
        FileServiceImpl.instance = this;
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
        return path.substring(path.lastIndexOf('/') + 15);
    }

    @Override
    public String getLastModificationDate(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        Date date = new Date(Long.valueOf(path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('/') + 14)));
        return new SimpleDateFormat(DateUtils.DATE_FORMAT).format(date);
    }

    @Override
    public String getUrl(final String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        return fileUrlPrefix + path.substring(uploadDirectory.getAbsolutePath().length() + 1);
    }

    @Override
    public String getPathFromUrl(final String url) {
        return uploadDirectory.getAbsolutePath() + "/" + url.substring(url.indexOf('/') + fileUrlPrefix.length() - 1);
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
        File directory = new File(uploadDirectory, MultiTenantUtil.getCurrentTenantId() + "/" + date.charAt(date.length() - 1)
                + "/" + date.charAt(date.length() - 2) + "/");
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

}
