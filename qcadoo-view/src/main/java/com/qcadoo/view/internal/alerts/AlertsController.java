package com.qcadoo.view.internal.alerts;

import com.qcadoo.view.internal.alerts.model.AlertDto;
import com.qcadoo.view.internal.alerts.utils.AlertsDbHelper;
import org.codehaus.jackson.map.ObjectMapper;
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

    @Autowired
    private AlertsDbHelper alertsDbHelper;

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
            alertsDbHelper.registerAlert(alertDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer theStringBuffer = new StringBuffer();
        HashMap theHashMap = new HashMap();

        // add the callback function name and open paren
        theStringBuffer.append(inCallback);
        theStringBuffer.append("('");

        // add the JSON string and close parens
        theStringBuffer.append("Alert register");
        theStringBuffer.append("')");

        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.setContentType(new MediaType());

        responseHeaders.add("Content-Type", "application/json;charset=utf-8");

        return new ResponseEntity(theStringBuffer.toString(), responseHeaders, HttpStatus.OK);
    }

}
