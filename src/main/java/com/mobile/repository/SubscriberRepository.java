package com.mobile.repository;

import com.mobile.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findAll(); // Цей метод вже є в JpaRepository, але можна явно оголосити
}