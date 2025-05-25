package com.mobile.controller;

import com.mobile.service.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subscribers")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping
    public String listSubscribers(Model model) {
        model.addAttribute("subscribers", subscriberService.findAll());
        return "subscribers/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteSubscriber(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subscriberService.deleteById(id); // Змінили з deleteSubscriber на deleteById
            redirectAttributes.addFlashAttribute("successMessage", "Subscriber deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting subscriber: " + e.getMessage());
        }
        return "redirect:/subscribers";
    }
}