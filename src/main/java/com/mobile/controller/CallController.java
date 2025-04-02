package com.mobile.controller;

import com.mobile.entity.Call;
import com.mobile.entity.Subscriber;
import com.mobile.service.CallService;
import com.mobile.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/calls")
public class CallController {
    private final CallService callService;
    private final SubscriberService subscriberService;

    @Autowired
    public CallController(CallService callService, SubscriberService subscriberService) {
        this.callService = callService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/subscriber/{subscriberId}")
    public String getCallsBySubscriber(@PathVariable Long subscriberId, Model model) {
        model.addAttribute("calls", callService.findBySubscriberId(subscriberId));
        model.addAttribute("subscriber", subscriberService.findById(subscriberId).orElseThrow());
        return "calls/list";
    }

    @PostMapping("/add")
    public String addCall(
            @RequestParam Long subscriberId,
            @RequestParam int duration,
            @RequestParam String callDate) {
        
        Subscriber subscriber = subscriberService.findById(subscriberId).orElseThrow();
        Call call = new Call(LocalDateTime.parse(callDate), duration, 0, subscriber);
        call.setCost(calculateCallCost(duration, subscriber.getTariff().getCallRate()));
        callService.save(call);
        
        // Update subscriber balance
        subscriber.setBalance(subscriber.getBalance() - call.getCost());
        subscriberService.save(subscriber);
        
        return "redirect:/calls/subscriber/" + subscriberId;
    }

    private double calculateCallCost(int duration, double rate) {
        return duration * rate / 60; // cost per second
    }
}