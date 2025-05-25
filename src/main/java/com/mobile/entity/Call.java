package com.mobile.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime callDate;
    private int duration;
    private double cost;
    
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;


    public Call() {
    }

    public Call(LocalDateTime callDate, int duration, double cost, Subscriber subscriber) {
        this.callDate = callDate;
        this.duration = duration;
        this.cost = cost;
        this.subscriber = subscriber;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDateTime callDate) {
        this.callDate = callDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    
}