package com.qcadoo.view.internal.alerts;

import com.google.common.collect.Lists;
import com.qcadoo.view.internal.alerts.model.AlertDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/alert")
public class AlertsController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlertDto> getAlert(@RequestParam String user) {
       List<AlertDto> alerts = Lists.newArrayList();
        AlertDto alertDto = new AlertDto();
        alertDto.setMessage("test");
        alerts.add(alertDto);
        alertDto.setType("01info");
        return alerts;
    }

}
