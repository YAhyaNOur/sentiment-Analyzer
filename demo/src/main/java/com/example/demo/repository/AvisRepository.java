package com.example.demo.repository;

import com.example.demo.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Long> {

    // avis d’un produit
    List<Avis> findByProduit_IdOrderByCreatedAtDesc(Long produitId);

    // avis d’un client
    List<Avis> findByClient_IdOrderByCreatedAtDesc(Long clientId);
    //nombre d’avis d’un produit
    long countByProduit_Id(Long produitId);
}