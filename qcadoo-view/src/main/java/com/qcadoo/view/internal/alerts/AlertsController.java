package com.qcadoo.view.internal.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import com.qcadoo.view.api.notifications.NotificationContainer;
import com.qcadoo.view.api.notifications.NotificationService;
import com.qcadoo.view.internal.alerts.model.AlertDto;
import com.qcadoo.view.internal.alerts.utils.AlertsDbHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/alert")
public class AlertsController {

    private String NOTIFICATION_REGISTER_SUCCESS = "NOTIFICATION REGISTER SUCCESS";

    private String NOTIFICATION_REGISTER_ERROR = "NOTIFICATION REGISTER ERROR";

    @Autowired
    private AlertsDbHelper alertsDbHelper;

    @Autowired
    private NotificationService notificationService;

    @ResponseBody
    @RequestMapping(value = "/systemNotifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public NotificationContainer getSystemNotifications() {
        return notificationService.getNotification();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlertDto> getAlert() {
        List<AlertDto> alerts = alertsDbHelper.getAlerts();
        alertsDbHelper.createViewedAlerts(alerts);
        return alerts;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerAlert(@RequestParam("callback") String inCallback, @RequestParam("data") String data) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            AlertDto alertDto = mapper.readValue(data, AlertDto.class);
            alertDto.setMessage(new String(BaseEncoding.base64Url().decode(alertDto.getMessage()), "utf-8"));
            alertsDbHelper.registerAlert(alertDto);
            return createResponse(inCallback, NOTIFICATION_REGISTER_SUCCESS, HttpStatus.OK);
        } catch (IOException e) {
            return createResponse(inCallback, NOTIFICATION_REGISTER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private ResponseEntity createResponse(String inCallback, String statusCode, HttpStatus status) {
        StringBuffer theStringBuffer = new StringBuffer();
        HashMap theHashMap = new HashMap();

        // add the callback function name and open paren
        theStringBuffer.append(inCallback);
        theStringBuffer.append("('");

        // add the JSON string and close parens
        theStringBuffer.append(statusCode);
        theStringBuffer.append("')");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=utf-8");

        return new ResponseEntity(theStringBuffer.toString(), responseHeaders, status);
    }

}
