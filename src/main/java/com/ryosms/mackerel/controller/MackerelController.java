package com.ryosms.mackerel.controller;

import com.ryosms.mackerel.service.MackerelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MackerelController {

    private final MackerelService mackerelService;

    @RequestMapping("/")
    public String serviceList(Model model) {
        model.addAttribute("serviceList", mackerelService.loadServiceList());

        return "service-list";
    }

}
