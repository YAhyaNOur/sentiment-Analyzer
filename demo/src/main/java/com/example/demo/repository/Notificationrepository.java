package com.example.demo.repository;

import com.example.demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Notificationrepository extends JpaRepository<Notification, Long> {
    List<Notification> findByVendeurIdOrderByCreatedAtDesc(Long vendeurId);
    List<Notification> findByVendeurIdAndLuFalseOrderByCreatedAtDesc(Long vendeurId);
    long countByVendeurIdAndLuFalse(Long vendeurId);
}