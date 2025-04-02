package com.mobile.service;

import com.mobile.entity.Call;
import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.mobile.repository.CallRepository;
import com.mobile.repository.SmsRepository;
import com.mobile.repository.SubscriberRepository;
import com.mobile.repository.TariffRepository;
import com.mobile.service.TariffService;
import com.mobile.service.SubscriberService;
import com.mobile.service.CallService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CallServiceIntegrationTest {

    @Autowired
    private CallRepository callRepository;
    
    @Autowired
    private SmsRepository smsRepository;
    
    @Autowired
    private SubscriberRepository subscriberRepository;
    
    @Autowired
    private TariffRepository tariffRepository;
    
    @Autowired
    private TariffService tariffService;
    
    @Autowired
    private SubscriberService subscriberService;
    
    @Autowired
    private CallService callService;

 @BeforeEach
    public void cleanup() {
        // Clear tables in reverse order of foreign key dependencies
        callRepository.deleteAll();
        smsRepository.deleteAll();
        subscriberRepository.deleteAll();
        tariffRepository.deleteAll();
    }
    @Test
    public void saveCall_ShouldPersistCall() {
        // 1. Спочатку створюємо та зберігаємо Tariff
        Tariff tariff = new Tariff("Test", 0.5, 0.1);
        tariff = tariffService.save(tariff);
        
        // 2. Потім створюємо та зберігаємо Subscriber з посиланням на Tariff
        Subscriber subscriber = new Subscriber("+380991234567", "Test", 100.0, tariff);
        subscriber = subscriberService.save(subscriber);
        
        // 3. Тепер створюємо Call з посиланням на Subscriber
        Call call = new Call(LocalDateTime.now(), 10, 5.0, subscriber);

        // 4. Зберігаємо Call
        Call savedCall = callService.save(call);

        assertNotNull(savedCall.getId());
        assertEquals(10, savedCall.getDuration());
    }

    @Test
    public void findBySubscriberId_ShouldReturnCalls() {
        // 1. Створюємо та зберігаємо Tariff
        Tariff tariff = new Tariff("Test", 0.5, 0.1);
        tariff = tariffService.save(tariff);
        
        // 2. Створюємо та зберігаємо Subscriber
        Subscriber subscriber = new Subscriber("+380671234567", "Test2", 50.0, tariff);
        subscriber = subscriberService.save(subscriber);
        
        // 3. Створюємо та зберігаємо два дзвінки
        Call call1 = new Call(LocalDateTime.now(), 5, 2.5, subscriber);
        Call call2 = new Call(LocalDateTime.now(), 15, 7.5, subscriber);
        callService.save(call1);
        callService.save(call2);

        // 4. Отримуємо дзвінки по subscriberId
        List<Call> calls = callService.findBySubscriberId(subscriber.getId());

        assertEquals(2, calls.size());
    }
}