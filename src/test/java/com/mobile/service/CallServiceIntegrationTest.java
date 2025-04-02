package com.mobile.service;

import com.mobile.entity.Call;
import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CallServiceIntegrationTest {

    @Autowired
    private CallService callService;

    @Autowired
    private SubscriberService subscriberService;

    @Test
    void saveCall_ShouldPersistCall() {
        // Arrange
        Tariff tariff = new Tariff("Test", 0.5, 0.1);
        Subscriber subscriber = new Subscriber("123456789", "Test", 100.0, tariff);
        subscriber = subscriberService.save(subscriber);
        
        Call call = new Call(LocalDateTime.now(), 10, 5.0, subscriber);

        // Act
        Call savedCall = callService.save(call);

        // Assert
        assertNotNull(savedCall.getId());
        assertEquals(10, savedCall.getDuration());
    }

    @Test
    void findBySubscriberId_ShouldReturnCalls() {
        // Arrange
        Tariff tariff = new Tariff("Test", 0.5, 0.1);
        Subscriber subscriber = new Subscriber("987654321", "Test2", 50.0, tariff);
        subscriber = subscriberService.save(subscriber);
        
        Call call1 = new Call(LocalDateTime.now(), 5, 2.5, subscriber);
        Call call2 = new Call(LocalDateTime.now(), 15, 7.5, subscriber);
        callService.save(call1);
        callService.save(call2);

        // Act
        List<Call> calls = callService.findBySubscriberId(subscriber.getId());

        // Assert
        assertEquals(2, calls.size());
    }
}