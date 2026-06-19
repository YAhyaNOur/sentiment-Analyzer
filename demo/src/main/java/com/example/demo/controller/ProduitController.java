package com.example.demo.controller;

import com.example.demo.entity.Produit;
import com.example.demo.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    // POST /api/produits — vendeur crée un produit

    @PostMapping
    public ResponseEntity<Produit> create(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(produitService.create(body, user.getUsername()));
    }

    // GET /api/produits — liste tous les produits (public)
    @GetMapping
    public ResponseEntity<List<Produit>> getAll() {
        return ResponseEntity.ok(produitService.getAll());
    }

    // GET /api/produits/mes — produits du vendeur connecté
    @GetMapping("/mes")
    public ResponseEntity<List<Produit>> getMes(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(produitService.getMesProduits(user.getUsername()));
    }

    // GET /api/produits/{id} — détail d'un produit
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    // PUT /api/produits/{id} — vendeur modifie un produit
    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(produitService.update(id, body, user.getUsername()));
    }

    // DELETE /api/produits/{id} — vendeur supprime un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}