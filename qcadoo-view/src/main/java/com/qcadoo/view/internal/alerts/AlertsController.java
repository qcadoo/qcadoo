package com.qcadoo.view.internal.alerts;

import com.qcadoo.view.internal.alerts.model.AlertDto;
import com.qcadoo.view.internal.alerts.utils.AlertsDbHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/alert")
public class AlertsController {

    @Autowired
    private AlertsDbHelper alertsDbHelper;
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlertDto> getAlert() {
       return alertsDbHelper.getAlerts();
    }

}
