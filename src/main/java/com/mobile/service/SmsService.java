package com.mobile.service;

import com.mobile.entity.Sms;
import com.mobile.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmsService {
    private final SmsRepository repository;

    @Autowired
    public SmsService(SmsRepository repository) {
        this.repository = repository;
    }

    public Sms save(Sms sms) {
        return repository.save(sms);
    }

    public List<Sms> findAll() {
        return repository.findAll();
    }

    public List<Sms> findBySubscriberId(Long subscriberId) {
        return repository.findBySubscriberId(subscriberId);
    }

    @Transactional
    public void deleteBySubscriberId(Long subscriberId) {
        repository.deleteBySubscriberId(subscriberId);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}