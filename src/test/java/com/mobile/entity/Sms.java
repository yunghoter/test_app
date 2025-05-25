package com.mobile.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SmsTest {
    @Test
    void testSmsCreation() {
        Subscriber subscriber = new Subscriber();
        LocalDateTime now = LocalDateTime.now();
        Sms sms = new Sms(now, "recipient", "Hello", 0.5, subscriber);
        
        assertEquals(now, sms.getSentDate());
        assertEquals("recipient", sms.getRecipient());
        assertEquals("Hello", sms.getContent());
        assertEquals(0.5, sms.getCost());
        assertEquals(subscriber, sms.getSubscriber());
    }

    @Test
    void testOverloadedConstructors() {
        Subscriber subscriber = new Subscriber();
        LocalDateTime now = LocalDateTime.now();
        
        Sms sms1 = new Sms(now, 1, subscriber);
        assertEquals(1.0, sms1.getCost());
        
        Sms sms2 = new Sms(now, 2.5, subscriber);
        assertEquals(2.5, sms2.getCost());
    }
}