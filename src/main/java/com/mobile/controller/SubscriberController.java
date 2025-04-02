package com.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mobile.entity.Subscriber;
import com.mobile.service.SubscriberService;
import com.mobile.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final CallService callService;
    
    @Autowired
    public SubscriberController(SubscriberService subscriberService, 
                              CallService callService) {
        this.subscriberService = subscriberService;
        this.callService = callService;
    }
    
    @GetMapping
    public String listSubscribers(Model model) {
        model.addAttribute("subscribers", subscriberService.findAll()); // Використовуємо правильні лапки
        return "subscribers/list";
    }
    
    @GetMapping("/{id}/calls")
    public String viewCalls(@PathVariable Long id, Model model) {
        model.addAttribute("calls", callService.findBySubscriberId(id));
        return "subscribers/calls";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "subscribers/add-form";
    }
    
    @PostMapping("/add")
    public String addSubscriber(@ModelAttribute Subscriber subscriber) {
        subscriberService.save(subscriber);
        return "redirect:/subscribers";
    }
}