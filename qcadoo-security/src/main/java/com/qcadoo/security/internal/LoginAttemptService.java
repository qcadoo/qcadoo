package com.qcadoo.security.internal;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private enum LockAccountMode {
        IP, USER, BOTH
    }

    private static final int DEFAULT_LOCK_ACCOUNT_TIME_MINUTES = 1440;

    private static final String DEFAULT_LOCK_ACCOUNT_MODE = "IP";

    private int maxUnsuccessfullAttempts;

    private LockAccountMode lockAccountMode;

    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        initAttemptsCache(DEFAULT_LOCK_ACCOUNT_TIME_MINUTES);
        setLockAccountMode(DEFAULT_LOCK_ACCOUNT_MODE);
    }

    private void initAttemptsCache(final int lockAccountTimeMinutes) {
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(lockAccountTimeMinutes, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {

                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void setLockAccountTimeMinutes(final int lockAccountTimeMinutes) {
        initAttemptsCache(lockAccountTimeMinutes);
    }

    public void setMaxUnsuccessfullAttempts(final int maxUnsuccessfullAttempts) {
        this.maxUnsuccessfullAttempts = maxUnsuccessfullAttempts;
    }

    public void setLockAccountMode(final String lockAccountMode) {
        this.lockAccountMode = LockAccountMode.valueOf(lockAccountMode.toUpperCase());
    }

    public void loginSucceeded(final String username, final String ipAddress) {
        String key = getKey(username, ipAddress);

        attemptsCache.invalidate(key);
    }

    public void loginFailed(final String username, final String ipAddress) {
        String key = getKey(username, ipAddress);

        int attempts = 0;

        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }

        attempts++;

        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String username, final String ipAddress) {
        String key = getKey(username, ipAddress);

        try {
            return attemptsCache.get(key) > maxUnsuccessfullAttempts;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public String getClientIP(final HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");

        if (Objects.isNull(xfHeader)) {
            return request.getRemoteAddr();
        }

        return xfHeader.split(",")[0];
    }

    private String getKey(final String username, final String ipAddress) {
        switch (this.lockAccountMode) {
            case IP:
                return ipAddress;

            case USER:
                return username;

            case BOTH:
                return ipAddress + username;

            default:
                return ipAddress;
        }
    }

}
