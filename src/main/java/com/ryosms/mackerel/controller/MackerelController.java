package com.ryosms.mackerel.controller;

import com.ryosms.mackerel.service.MackerelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class MackerelController {

    private final MackerelService mackerelService;

    @RequestMapping("/")
    public String serviceList(Model model) {
        model.addAttribute("serviceList", mackerelService.loadServiceList());

        return "service-list";
    }

    @RequestMapping(value = "/services/{serviceName}", method = RequestMethod.GET)
    public String metricList(@PathVariable String serviceName, Model model) {
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("metricNames", mackerelService.loadMetricList(serviceName));

        return "metric-list";
    }

}
