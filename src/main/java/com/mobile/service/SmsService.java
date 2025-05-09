package com.mobile.service;

import com.mobile.entity.Sms;
import com.mobile.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsService {
    private final SmsRepository smsRepository;

    @Autowired
    public SmsService(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    public Sms save(Sms sms) {
        return smsRepository.save(sms);
    }

    public List<Sms> findBySubscriberId(Long subscriberId) {
        return smsRepository.findBySubscriberId(subscriberId);
    }

    public List<Sms> findAll() {
        return smsRepository.findAll();
    }

    public void deleteAll() {
        smsRepository.deleteAll();
    }
}
