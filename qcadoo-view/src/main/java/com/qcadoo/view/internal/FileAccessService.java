package com.qcadoo.view.internal;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.qcadoo.view.internal.controllers.FileAccessController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FileAccessService implements InitializingBean {

    private static final String L_FILE_URL_PREFIX = "/files";

    private Cache<String, String> accessTokens;

    @Value("${fileAccessToken.expirationTimeInSeconds}")
    private long expirationTimeInSeconds;

    @Value("${fileAccessToken.invalidateAfterAccess:false}")
    private boolean invalidateAfterAccess;

    public String createSecuredUrl(final String path) {
        String token = UUID.randomUUID().toString();

        accessTokens.put(token, path);

        return L_FILE_URL_PREFIX + FileAccessController.URL_PREFIX + "/" + token;
    }

    public String consumeTokenAndGetOriginalUrl(final String token) {
        String originalUrl = accessTokens.getIfPresent(token);

        if (invalidateAfterAccess) {
            accessTokens.invalidate(token);
        }

        return originalUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        accessTokens = CacheBuilder.newBuilder().expireAfterWrite(expirationTimeInSeconds, TimeUnit.SECONDS).build();
    }

}
