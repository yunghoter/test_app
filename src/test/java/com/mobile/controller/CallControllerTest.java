
package com.mobile.controller;

import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff; 
import com.mobile.service.CallService;
import com.mobile.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Collections; 

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl; 

@WebMvcTest(CallController.class)
public class CallControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallService callService;

    @MockBean
    private SubscriberService subscriberService;

   @Test
void getCallsBySubscriber_ShouldReturnView() throws Exception {
    Subscriber subscriber = new Subscriber();
    when(subscriberService.findById(1L)).thenReturn(Optional.of(subscriber));
    when(callService.findBySubscriberId(1L)).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/calls/subscriber/1"))
           .andExpect(status().isOk())
           .andExpect(view().name("calls/list"));
}

@Test
void addCall_ShouldRedirect() throws Exception {
    Subscriber subscriber = new Subscriber();
    subscriber.setId(1L);
    Tariff tariff = new Tariff("Standard", 0.1, 0.05);
    subscriber.setTariff(tariff);

    when(subscriberService.findById(1L)).thenReturn(Optional.of(subscriber));

    mockMvc.perform(post("/calls/add")
            .param("subscriberId", "1")
            .param("duration", "120")
            .param("callDate", "2023-01-01T12:00:00"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/calls/subscriber/1"));
}
}