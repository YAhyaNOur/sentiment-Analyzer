package com.example.demo.controller;

import com.example.demo.entity.Avis;
import com.example.demo.service.AvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    // POST /api/avis/{produitId} — client soumet un avis

    @PostMapping("/{produitId}")
    public ResponseEntity<Map<String, Object>> submit(
            @PathVariable Long produitId,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails user) {

        String texte = (String) body.get("texte");
        Integer note = ((Number) body.get("note")).intValue();

        return ResponseEntity.ok(
                avisService.submitAvis(produitId, texte, note, user.getUsername()));
    }

    // GET /api/avis/produit/{produitId} — tous les avis d'un produit
    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<Avis>> getByProduit(@PathVariable Long produitId) {
        return ResponseEntity.ok(avisService.getByProduit(produitId));
    }

    // GET /api/avis/mes — avis du client connecté
    @GetMapping("/mes")
    public ResponseEntity<List<Avis>> getMes(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(avisService.getMesAvis(user.getUsername()));
    }
}