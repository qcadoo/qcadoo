package com.qcadoo.plugin.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.io.InputStreamResource;

import com.google.common.base.Preconditions;

public class JarEntryResource extends InputStreamResource {

    /**
     * Original URL, used for actual access.
     */
    private final URL url;

    public JarEntryResource(final File file, final InputStream inputStream) throws MalformedURLException {
        super(inputStream, "URL and inputStream for jar entry [" + file.getAbsolutePath() + "]");
        Preconditions.checkNotNull(file, "file should not be null");
        this.url = file.toURI().toURL();
    }

    /**
     * This implementation returns the underlying URL reference.
     */
    @Override
    public URL getURL() throws IOException {
        return this.url;
    }

}
