package com.qcadoo.view.internal.resource.module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.qcadoo.view.internal.resource.ResourceModule;
import com.qcadoo.view.internal.resource.ResourceService;

public class UniversalResourceModule extends ResourceModule {

    private final ApplicationContext applicationContext;

    private final String uriPattern;

    private final PathMatcher matcher = new AntPathMatcher();

    public UniversalResourceModule(final ResourceService resourceService, final ApplicationContext applicationContext,
            final String pluginIdentifier, final String uriPattern) {
        super(resourceService);
        this.applicationContext = applicationContext;
        if (uriPattern.startsWith("/")) {
            this.uriPattern = "/" + pluginIdentifier + uriPattern;
        } else {
            this.uriPattern = "/" + pluginIdentifier + "/" + uriPattern;
        }

    }

    @Override
    public boolean serveResource(final HttpServletRequest request, final HttpServletResponse response) {
        Resource resource = getResourceFromURI(request.getRequestURI());
        if (resource != null && resource.exists()) {
            response.setContentType(getContentTypeFromURI(request));
            try {
                copy(resource.getInputStream(), response.getOutputStream());
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            return true;
        } else {
            return false;
        }
    }

    private Resource getResourceFromURI(final String uri) {
        if (matcher.match(uriPattern, uri)) {
            return applicationContext.getResource("classpath:" + uri);
        }
        return null;
    }

    private String getContentTypeFromURI(final HttpServletRequest request) {
        String[] arr = request.getRequestURI().split("\\.");
        String ext = arr[arr.length - 1];
        if ("js".equals(ext)) {
            return "text/javascript";
        } else if ("css".equals(ext)) {
            return "text/css";
        } else {
            return URLConnection.guessContentTypeFromName(request.getRequestURL().toString());
        }
    }

    private static final int IO_BUFFER_SIZE = 4 * 1024;

    private void copy(final InputStream in, final OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}
