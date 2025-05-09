package com.mobile.controller;

import com.mobile.service.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/subscribers")
    public String listSubscribers(Model model) {
        model.addAttribute("subscribers", subscriberService.findAll());
        return "subscribers/list";
    }
}