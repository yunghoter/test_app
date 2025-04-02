package com.mobile.service;

import com.mobile.entity.Tariff;
import com.mobile.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {
    private final TariffRepository repository;

    @Autowired
    public TariffService(TariffRepository repository) {
        this.repository = repository;
    }

    public List<Tariff> findAll() {
        return repository.findAll();
    }

    public Optional<Tariff> findById(Long id) {
        return repository.findById(id);
    }

    public Tariff save(Tariff tariff) {
        return repository.save(tariff);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

     public void deleteAll() {
        repository.deleteAll();
    }
}