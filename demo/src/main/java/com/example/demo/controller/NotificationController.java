package com.example.demo.controller;

import com.example.demo.entity.Notification;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAll(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(
                notificationService.getMesNotifications(user.getUsername()));
    }

    @GetMapping("/nonlues")
    public ResponseEntity<Map<String, Object>> getNonLues(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(
                notificationService.getNonLues(user.getUsername()));
    }

    @PutMapping("/{id}/lu")
    public ResponseEntity<Void> marquerLu(@PathVariable Long id) {
        notificationService.marquerLu(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/toutlu")
    public ResponseEntity<Void> marquerToutLu(
            @AuthenticationPrincipal UserDetails user) {
        notificationService.marquerToutLu(user.getUsername());
        return ResponseEntity.ok().build();
    }
}