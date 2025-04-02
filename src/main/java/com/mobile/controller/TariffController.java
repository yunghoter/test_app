package com.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobile.service.TariffService;
import com.mobile.service.SubscriberService;
import com.mobile.service.CallService;

@RestController
@RequestMapping("/tariffs")
public class TariffController {
    @Autowired private TariffService tariffService;

    @GetMapping
    public String listTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.findAll());
        return "tariffs/list";
    }
}