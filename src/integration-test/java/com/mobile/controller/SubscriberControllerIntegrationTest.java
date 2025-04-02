package com.mobile.controller;

import com.mobile.MainApp;
import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff;
import com.mobile.service.SubscriberService;
import com.mobile.service.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MainApp.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SubscriberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private SubscriberService subscriberService;
    
    @Autowired
    private TariffService tariffService;
    
    private Tariff tariff;
    private Subscriber subscriber;

    @BeforeEach
    void setUp() {
        tariff = new Tariff("Тестовий", 0.5, 0.1);
        tariff = tariffService.save(tariff);
        
        subscriber = new Subscriber("+380991234567", "Тестовий абонент", 100.0, tariff);
        subscriber = subscriberService.save(subscriber);
    }

    @Test
    void whenGetSubscribers_thenReturnOk() throws Exception {
        mockMvc.perform(get("/subscribers"))
               .andExpect(status().isOk())
               .andExpect(view().name("subscribers/list"))
               .andExpect(model().attributeExists("subscribers"));
    }

    @Test
    void whenAddSubscriber_thenRedirectToList() throws Exception {
        mockMvc.perform(post("/subscribers/add")
                .param("phoneNumber", "+380671234567")
                .param("name", "Новий абонент")
                .param("balance", "50.0")
                .param("tariff.id", tariff.getId().toString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/subscribers"));
    }
}