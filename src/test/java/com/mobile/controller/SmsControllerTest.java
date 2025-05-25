package com.mobile.controller;

import com.mobile.entity.Sms;
import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff;
import com.mobile.service.SmsService;
import com.mobile.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SmsController.class)
public class SmsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmsService smsService;

    @MockBean
    private SubscriberService subscriberService;

    @Test
    void getSmsBySubscriber_ShouldReturnView() throws Exception {
        Subscriber subscriber = new Subscriber();
        when(subscriberService.findById(1L)).thenReturn(Optional.of(subscriber));
        when(smsService.findBySubscriberId(1L)).thenReturn(List.of(new Sms()));

        mockMvc.perform(get("/sms/subscriber/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("sms/list"));
    }

    @Test
    void testAddSms() throws Exception {
        Tariff tariff = new Tariff("Test", 0.1, 0.5);
        Subscriber subscriber = new Subscriber("+380001112233", "Test", 100.0, tariff);
        
        when(subscriberService.findById(1L)).thenReturn(Optional.of(subscriber));
        when(subscriberService.save(any(Subscriber.class))).thenAnswer(invocation -> {
            Subscriber s = invocation.getArgument(0);
            subscriber.setBalance(s.getBalance());
            return s;
        });
        
        mockMvc.perform(post("/sms/add")
                .param("subscriberId", "1")
                .param("fromNumber", "+380001112233")
                .param("toNumber", "+380002223344"))
               .andExpect(status().is3xxRedirection());
        
        assertEquals(99.5, subscriber.getBalance(), 0.001);
        verify(smsService, times(1)).save(any(Sms.class));
        verify(subscriberService, times(1)).save(any(Subscriber.class));
    }
}