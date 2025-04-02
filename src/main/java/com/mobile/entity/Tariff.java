package com.mobile.entity;

import javax.persistence.*;

@Entity
@Table(name = "tariffs")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double callRate;
    private double smsRate;

    // Конструкторы
    public Tariff() {
    }

    public Tariff(String name, double callRate, double smsRate) {
        this.name = name;
        this.callRate = callRate;
        this.smsRate = smsRate;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCallRate() {
        return callRate;
    }

    public void setCallRate(double callRate) {
        this.callRate = callRate;
    }

    public double getSmsRate() {
        return smsRate;
    }

    public void setSmsRate(double smsRate) {
        this.smsRate = smsRate;
    }
}