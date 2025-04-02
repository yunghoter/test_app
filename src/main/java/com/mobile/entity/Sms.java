package com.mobile.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms")
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sentDate;
    private String recipient;
    private String content;
    private double cost;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    // Конструктори
    public Sms() {
    }
public Sms(LocalDateTime sentDate, double cost, Subscriber subscriber) {
        this(sentDate, "SYSTEM", "NO_CONTENT", cost, subscriber);
    }

    public Sms(LocalDateTime sentDate, int cost, Subscriber subscriber) {
        this(sentDate, "SYSTEM", "NO_CONTENT", (double)cost, subscriber);
    }
    public Sms(LocalDateTime sentDate, String recipient, String content, double cost, Subscriber subscriber) {
        this.sentDate = sentDate;
        this.recipient = recipient;
        this.content = content;
        this.cost = cost;
        this.subscriber = subscriber;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
