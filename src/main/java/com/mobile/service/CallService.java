package com.mobile.service;

import com.mobile.entity.Call;
import com.mobile.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {
    private final CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public Call save(Call call) {
        return callRepository.save(call);
    }

    public List<Call> findBySubscriberId(Long subscriberId) {
        return callRepository.findBySubscriberId(subscriberId);
    }

	  public void deleteAll() {
        callRepository.deleteAll();
    }
}