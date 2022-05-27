package com.qcadoo.security.internal.listeners;

import com.qcadoo.security.internal.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();

        UsernamePasswordAuthenticationToken source = (UsernamePasswordAuthenticationToken) e.getSource();

        String username;
        String ipAddress = auth.getRemoteAddress();

        if (Objects.nonNull(source)) {
            Object principal = source.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                username = user.getUsername();
            } else {
                username = (String) principal;
            }
        } else {
            username = "unknown";
        }

        loginAttemptService.loginFailed(username, ipAddress);
    }

}