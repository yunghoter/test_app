package com.mobile.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CallTest {
    @Test
    void testCallCreation() {
        Subscriber subscriber = new Subscriber();
        LocalDateTime now = LocalDateTime.now();
        Call call = new Call(now, 120, 5.0, subscriber);
        
        assertEquals(now, call.getCallDate());
        assertEquals(120, call.getDuration());
        assertEquals(5.0, call.getCost());
        assertEquals(subscriber, call.getSubscriber());
    }

    @Test
    void testSetters() {
        Call call = new Call();
        Subscriber subscriber = new Subscriber();
        
        call.setId(1L);
        call.setCallDate(LocalDateTime.now());
        call.setDuration(60);
        call.setCost(2.5);
        call.setSubscriber(subscriber);
        
        assertEquals(1L, call.getId());
        assertEquals(60, call.getDuration());
        assertEquals(2.5, call.getCost());
        assertEquals(subscriber, call.getSubscriber());
    }
	 @Test
void testCallEntity() {
    Call call = new Call();
    call.setId(1L);
    call.setDuration(60);
    call.setCost(1.5);
    
    assertEquals(1L, call.getId());
    assertEquals(60, call.getDuration());
    assertEquals(1.5, call.getCost());
}


}