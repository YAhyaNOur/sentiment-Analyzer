package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AvisService {

    private final AvisRepository avisRepository;
    private final AnalyseRepository analyseRepository;
    private final ProduitRepository produitRepository;
    private final UserRepository userRepository;
    private final Notificationrepository notificationrepository;
    private final WebClient webClient;

    @Value("${python.ai.url}")
    private String pythonUrl;

    public AvisService(AvisRepository avisRepository,
                       AnalyseRepository analyseRepository,
                       ProduitRepository produitRepository,
                       UserRepository userRepository,
                       Notificationrepository notificationrepository,
                       WebClient webClient) {
        this.avisRepository = avisRepository;
        this.analyseRepository = analyseRepository;
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
        this.notificationrepository = notificationrepository;
        this.webClient = webClient;
    }

    @Transactional
    public Map<String, Object> submitAvis(Long produitId, String texte,
                                          Integer note, String email) {
        // 1. Récupérer client et produit
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client introuvable"));

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + produitId));

        // 2. Sauvegarder l'avis
        Avis avis = new Avis();
        avis.setTexte(texte);
        avis.setNote(note);
        avis.setProduit(produit);
        avis.setClient(client);
        avisRepository.save(avis);

        // 3. Appeler Python pour l'analyse sentiment
        Map<String, Object> result = callPython(String.valueOf(produitId), texte);

        // 4. Sauvegarder l'analyse
        String keywords = "";
        if (result.containsKey("keywords")) {
            keywords = String.join(",", (List<String>) result.get("keywords"));
        }

        Analyse analyse = new Analyse();
        analyse.setSentiment((String) result.getOrDefault("sentiment", "neutre"));
        analyse.setScore(((Number) result.getOrDefault("score", 0.5)).doubleValue());
        analyse.setLabel(((Number) result.getOrDefault("label", 1)).intValue());
        analyse.setKeywords(keywords);
        analyse.setAvis(avis);
        analyseRepository.save(analyse);

        // 5. Notification si négatif
        if ("négatif".equals(analyse.getSentiment())) {
            Notification notif = new Notification();
            notif.setMessage("⚠️ Avis négatif sur \"" + produit.getNom()
                    + "\" — score: " + analyse.getScore());
            notif.setVendeur(produit.getVendeur());
            notif.setProduitId(produitId);
            notif.setAvisId(avis.getId());
            notificationrepository.save(notif);
            log.info("Notification créée pour vendeur {} — produit {}",
                    produit.getVendeur().getEmail(), produit.getNom());
        }

        log.info("Avis {} — sentiment: {} score: {}",
                avis.getId(), analyse.getSentiment(), analyse.getScore());

        return Map.of(
                "avisId",    avis.getId(),
                "sentiment", analyse.getSentiment(),
                "score",     analyse.getScore(),
                "keywords",  analyse.getKeywordsList()
        );
    }

    private Map<String, Object> callPython(String productId, String text) {
        try {
            Map result = webClient.post()
                    .uri("/api/ai/sentiment")
                    .bodyValue(Map.of("product_id", productId, "text", text))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            return result != null ? result : Map.of();
        } catch (Exception e) {
            log.error("Erreur Python : {}", e.getMessage());
            return Map.of("sentiment", "neutre", "score", 0.5, "label", 1);
        }
    }

    public List<Avis> getByProduit(Long produitId) {
        return avisRepository.findByProduit_IdOrderByCreatedAtDesc(produitId);
    }

    public List<Avis> getMesAvis(String email) {
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client introuvable"));
        return avisRepository.findByClient_IdOrderByCreatedAtDesc(client.getId());
    }
}