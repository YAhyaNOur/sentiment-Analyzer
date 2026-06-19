package com.example.demo.service;

import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import com.example.demo.repository.Notificationrepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Notificationrepository notificationRepository;
    private final UserRepository userRepository;

    public List<Notification> getMesNotifications(String email) {
        User vendeur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendeur introuvable"));
        return notificationRepository
                .findByVendeurIdOrderByCreatedAtDesc(vendeur.getId());
    }

    public Map<String, Object> getNonLues(String email) {
        User vendeur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendeur introuvable"));
        long count = notificationRepository
                .countByVendeurIdAndLuFalse(vendeur.getId());
        List<Notification> notifs = notificationRepository
                .findByVendeurIdAndLuFalseOrderByCreatedAtDesc(vendeur.getId());
        return Map.of("count", count, "notifications", notifs);
    }

    public void marquerLu(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        n.setLu(true);
        notificationRepository.save(n);
    }

    public void marquerToutLu(String email) {
        User vendeur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendeur introuvable"));
        List<Notification> notifs = notificationRepository
                .findByVendeurIdAndLuFalseOrderByCreatedAtDesc(vendeur.getId());
        notifs.forEach(n -> n.setLu(true));
        notificationRepository.saveAll(notifs);
    }
}