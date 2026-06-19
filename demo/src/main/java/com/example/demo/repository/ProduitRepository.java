package com.example.demo.repository;

import com.example.demo.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByVendeurIdOrderByCreatedAtDesc(Long vendeurId);
    List<Produit> findAllByOrderByCreatedAtDesc();
}