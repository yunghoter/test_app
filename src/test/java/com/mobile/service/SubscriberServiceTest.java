package com.mobile.service;

import com.mobile.entity.Subscriber;
import com.mobile.entity.Tariff;
import com.mobile.repository.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubscriberServiceTest {

    @Mock
    private SubscriberRepository repository;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private SubscriberService subscriberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnSubscriber_WhenExists() {

        Subscriber subscriber = new Subscriber("123456789", "Test", 100.0, new Tariff());
        when(repository.findById(1L)).thenReturn(Optional.of(subscriber));


        Optional<Subscriber> found = subscriberService.findById(1L);


        assertTrue(found.isPresent());
        assertEquals("123456789", found.get().getPhoneNumber());
    }

    @Test
    void save_ShouldReturnSavedSubscriber() {

        Subscriber subscriber = new Subscriber("123456789", "Test", 100.0, new Tariff());
        when(repository.save(subscriber)).thenReturn(subscriber);


        Subscriber saved = subscriberService.save(subscriber);


        assertNotNull(saved);
        assertEquals("123456789", saved.getPhoneNumber());
    }

    @Test
    void deleteAll_ShouldCallRepository() {

        subscriberService.deleteAll();


        verify(smsService, times(1)).deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void deleteById_ShouldCallRepository() {

        subscriberService.deleteById(1L);


        verify(smsService, times(1)).deleteBySubscriberId(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}