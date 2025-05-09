package com.mobile.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.mobile.entity.*;
import com.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TariffService tariffService;
    private final SubscriberService subscriberService;
    private final CallService callService;
    private final SmsService smsService;
    
    @Autowired
    public DataInitializer(TariffService tariffService, 
                         SubscriberService subscriberService,
                         CallService callService,
                         SmsService smsService) {
        this.tariffService = tariffService;
        this.subscriberService = subscriberService;
        this.callService = callService;
        this.smsService = smsService;
    }

@Override
@Transactional
public void run(String... args) {
    cleanupDatabase();
    System.out.println("Subscribers in DB: " + subscriberService.findAll().size());

    if (subscriberService.findAll().isEmpty()) {

        System.out.println("Initializing default data...");
        initializeData();
    } else {
        System.out.println("Data already exists, skipping initialization.");
    }
}

    private void initializeData() {
        // Очистка бази даних
        cleanupDatabase();
        
        // Створення тарифів
        Tariff basic = createTariff("Базовий", 0.5, 0.1);
        Tariff premium = createTariff("Преміум", 0.3, 0.05);
        
        // Створення абонентів
        Subscriber sub1 = createSubscriber("+380991234567", "Іван Петренко", 100.0, basic);
        Subscriber sub2 = createSubscriber("+380671234567", "Марія Коваленко", 50.0, premium);
        
        // Дзвінки
        createCall(sub1, LocalDateTime.now().minusDays(1), 120);
        createCall(sub2, LocalDateTime.now().minusHours(3), 45);
        
        // SMS
        createSms(sub1, "SYSTEM", "SUBSCRIBER");
        createSms(sub2, "SYSTEM", "SUBSCRIBER");

        System.out.println("Subscribers: " + subscriberService.findAll().size());
System.out.println("Calls: " + callService.findAll().size());
System.out.println("SMS: " + smsService.findAll().size());
    }




    private Tariff createTariff(String name, double callRate, double smsRate) {
        Tariff tariff = new Tariff(name, callRate, smsRate);
        return tariffService.save(tariff);
    }

    private Subscriber createSubscriber(String phone, String name, double balance, Tariff tariff) {
        Subscriber subscriber = new Subscriber(phone, name, balance, tariff);
        return subscriberService.save(subscriber);
    }

    private void createCall(Subscriber subscriber, LocalDateTime date, int duration) {
        double cost = duration * subscriber.getTariff().getCallRate() / 60;
        Call call = new Call(date, duration, cost, subscriber);
        callService.save(call);
    }

    private void createSms(Subscriber subscriber, String from, String to) {
        double cost = subscriber.getTariff().getSmsRate();
        Sms sms = new Sms(LocalDateTime.now(), from, to, cost, subscriber);
        smsService.save(sms);
    }

    private void cleanupDatabase() {
        // Змінений порядок очищення через foreign key constraints
        smsService.deleteAll();
        callService.deleteAll();
        subscriberService.deleteAll();
        tariffService.deleteAll();
    }
}