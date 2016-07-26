package com.qcadoo.view;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class FilesystemResourcesFilter implements Filter {

    private String sourceBasePath;

    private final Set EXTENSIONS = Sets.newHashSet("js", "css");

    @Override
    public void init(FilterConfig conf) {
        sourceBasePath = conf.getInitParameter("sourceBasePath");
        if (Strings.isNullOrEmpty(sourceBasePath)) {
            String currentDir = getClass().getClassLoader().getResource("").getPath();
            String currentDirSuffix = "/mes/mes-application/target/tomcat-archiver/mes-application/webapps/ROOT/WEB-INF/classes/";
            sourceBasePath = currentDir.substring(0, currentDir.length() - currentDirSuffix.length());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            if (!serveResource(httpRequest, (HttpServletResponse) response)) {
                chain.doFilter(request, response);
            }
        }
    }

    public boolean serveResource(final HttpServletRequest request, final HttpServletResponse response) {
        InputStream resource = getResourceFromURI(request.getRequestURI());

        if (resource != null) {
            response.setContentType(getContentTypeFromURI(request));
            try {
                IOUtils.copy(resource, response.getOutputStream());
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            } finally {
                try {
                    resource.close();
                } catch (IOException ignore) {
                }
            }
            return true;
        }
        return false;
    }

    private InputStream getResourceFromURI(final String uri) {
        int dotIndex = uri.lastIndexOf('.');

        if (dotIndex != -1) {
            String extension = uri.substring(dotIndex + 1);

            if (EXTENSIONS.contains(extension)) {
                List<String> prefixes = Arrays.asList("/mes/mes-plugins/", "/mes-commercial/", "/qcadoo/");

                for (String prefix : prefixes) {
                    Path dir = FileSystems.getDefault().getPath(sourceBasePath + prefix);
                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                        for (Path pluginMainDir : stream) {
                            Path file = pluginMainDir.resolve("src/main/resources/").resolve(uri.substring(1));
                            if (Files.exists(file)) {
                                return file.toUri().toURL().openStream();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
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

    @Override
    public void destroy() {
    }
}
