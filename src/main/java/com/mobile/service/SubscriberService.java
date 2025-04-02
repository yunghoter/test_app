package com.mobile.service;
import com.mobile.entity.Sms;

import com.mobile.entity.Subscriber;
import com.mobile.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private final SubscriberRepository repository;
	 private final SmsService smsService;


 	@Autowired
    public SubscriberService(SubscriberRepository repository, SmsService smsService) {
        this.repository = repository;
        this.smsService = smsService;
    }

    public List<Subscriber> findAll() {
        return repository.findAll();
    }

    public Optional<Subscriber> findById(Long id) {
        return repository.findById(id);
    }

    public Subscriber save(Subscriber subscriber) {
        return repository.save(subscriber);
    }
	   public Sms saveSms(Sms sms) {
        return smsService.save(sms);
    }

    // Метод для отримання SMS по ID підписника
    public List<Sms> findSmsBySubscriberId(Long subscriberId) {
        return smsService.findBySubscriberId(subscriberId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

	  public void deleteAll() {
        repository.deleteAll();
    }
}