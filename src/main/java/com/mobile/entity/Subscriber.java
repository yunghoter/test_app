package com.mobile.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    
    private String name;
    private double balance;
    
    @ManyToOne
    @JoinColumn(name = "tariff_id", nullable = false) // Додано nullable = false
    private Tariff tariff;
    
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private List<Call> calls;
    
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private List<Sms> smsList;

    // Конструкторы
    public Subscriber() {
    }

    public Subscriber(String phoneNumber, String name, double balance, Tariff tariff) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.balance = balance;
        this.tariff = tariff;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public void setCalls(List<Call> calls) {
        this.calls = calls;
    }

    public List<Sms> getSmsList() {
        return smsList;
    }

    public void setSmsList(List<Sms> smsList) {
        this.smsList = smsList;
    }
}