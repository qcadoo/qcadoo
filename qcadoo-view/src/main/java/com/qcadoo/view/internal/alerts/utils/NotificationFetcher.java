package com.qcadoo.view.internal.alerts.utils;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Strings;
import com.qcadoo.view.internal.alerts.model.AlertDto;

@Service
@Transactional
public class NotificationFetcher {

    @Value("${notificationSender.url}")
    private String url;

    @Value("${notificationSender.userName}")
    private String userName;

    @Value("${notificationSender.password}")
    private String password;

    @Value("${notificationSender.instanceName}")
    private String instanceName;

    @Autowired
    private AlertsDbHelper alertsDbHelper;

    public void fetch() {
        if (!Strings.isNullOrEmpty(url)) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<AlertDto>> alertResponse = restTemplate.exchange(url + "?instanceName=" + instanceName,
                    HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<List<AlertDto>>() {
                    });
            List<AlertDto> alerts = alertResponse.getBody();
            alerts.forEach(a -> {
                alertsDbHelper.registerAlert(a);
                restTemplate.exchange(url + a.getId(), HttpMethod.PUT, new HttpEntity<>(createHeaders()), Void.class);
            });
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        StringBuilder plainCredentials = new StringBuilder();

        plainCredentials.append(userName).append(":").append(password);

        byte[] plainCredentialsBytes = plainCredentials.toString().getBytes();
        byte[] base64CredentialsBytes = Base64.encodeBase64(plainCredentialsBytes);
        String base64Credentials = new String(base64CredentialsBytes);

        httpHeaders.add("Authorization", "Basic " + base64Credentials);
        return httpHeaders;
    }
}
