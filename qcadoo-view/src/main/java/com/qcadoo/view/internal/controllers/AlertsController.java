package com.qcadoo.view.internal.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcadoo.view.internal.model.AlertDto;

@Controller
@RequestMapping("/alert")
public class AlertsController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AlertDto getAlert(@RequestParam String user) {
        AlertDto alertDto = new AlertDto();
        alertDto.setMessage("test");
        alertDto.setType("01info");
        return alertDto;
    }

}
