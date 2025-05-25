
package com.mobile.controller;

import com.mobile.entity.Sms;
import com.mobile.entity.Subscriber;
import com.mobile.service.SmsService;
import com.mobile.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mobile.entity.Tariff;


import java.time.LocalDateTime;

@Controller
@RequestMapping("/sms")
public class SmsController {
    private final SmsService smsService;
    private final SubscriberService subscriberService;

    @Autowired
    public SmsController(SmsService smsService, SubscriberService subscriberService) {
        
        this.smsService = smsService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/subscriber/{subscriberId}")
    public String getSmsBySubscriber(@PathVariable Long subscriberId, Model model) {
        model.addAttribute("smsList", smsService.findBySubscriberId(subscriberId));
        model.addAttribute("subscriber", subscriberService.findById(subscriberId).orElseThrow());
        return "sms/list";
    }

    @PostMapping("/add")
public String addSms(
        @RequestParam Long subscriberId,
        @RequestParam String fromNumber,
        @RequestParam String toNumber) {
    
    Subscriber subscriber = subscriberService.findById(subscriberId).orElseThrow();
    double smsCost = subscriber.getTariff().getSmsRate(); 
    
    Sms sms = new Sms(LocalDateTime.now(), fromNumber, toNumber, smsCost, subscriber);
    smsService.save(sms);
    
    subscriber.setBalance(subscriber.getBalance() - smsCost);
    subscriberService.save(subscriber);
    
    return "redirect:/sms/subscriber/" + subscriberId;
}
}