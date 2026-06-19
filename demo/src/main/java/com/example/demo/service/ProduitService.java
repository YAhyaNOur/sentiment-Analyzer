package com.example.demo.service;

import com.example.demo.entity.Produit;
import com.example.demo.entity.User;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final UserRepository userRepository;

    public ProduitService(ProduitRepository produitRepository,
                          UserRepository userRepository) {
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
    }

    public Produit create(Map<String, String> body, String email) {
        User vendeur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendeur introuvable"));

        Produit produit = new Produit();
        produit.setNom(body.get("nom"));
        produit.setDescription(body.get("description"));
        produit.setPrix(body.get("prix") != null ? Double.parseDouble(body.get("prix")) : null);
        produit.setCategorie(body.get("categorie"));
        produit.setVendeur(vendeur);

        return produitRepository.save(produit);
    }

    public List<Produit> getAll() {
        return produitRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Produit> getMesProduits(String email) {
        User vendeur = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendeur introuvable"));
        return produitRepository.findByVendeurIdOrderByCreatedAtDesc(vendeur.getId());
    }

    public Produit getById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
    }

    public Produit update(Long id, Map<String, String> body, String email) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));

        if (body.containsKey("nom"))         produit.setNom(body.get("nom"));
        if (body.containsKey("description")) produit.setDescription(body.get("description"));
        if (body.containsKey("prix"))        produit.setPrix(Double.parseDouble(body.get("prix")));
        if (body.containsKey("categorie"))   produit.setCategorie(body.get("categorie"));

        return produitRepository.save(produit);
    }

    public void delete(Long id) {
        produitRepository.deleteById(id);
    }
}