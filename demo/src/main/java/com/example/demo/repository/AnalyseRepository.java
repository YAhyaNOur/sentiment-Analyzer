package com.example.demo.repository;

import com.example.demo.entity.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {

    List<Analyse> findByAvis_Produit_Id(Long produitId);

    @Query("SELECT COUNT(a) FROM Analyse a WHERE a.avis.produit.id = :produitId AND a.sentiment = :sentiment")
    long countBySentiment(@Param("produitId") Long produitId,
                          @Param("sentiment") String sentiment);

    @Query("SELECT a.keywords FROM Analyse a WHERE a.avis.produit.id = :produitId AND a.keywords IS NOT NULL")
    List<String> findKeywordsByProduitId(Long produitId);
}