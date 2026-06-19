package com.example.demo.service;

import com.example.demo.entity.Analyse;
import com.example.demo.repository.AnalyseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyseService {

    private final AnalyseRepository analyseRepository;

    public List<Analyse> getByProduit(Long produitId) {
        return analyseRepository.findByAvis_Produit_Id(produitId);
    }

    public Map<String, Object> getStats(Long produitId) {
        List<Analyse> analyses = analyseRepository.findByAvis_Produit_Id(produitId);
        long total = analyses.size();

        if (total == 0) {
            return Map.of("total", 0, "positif", 0, "neutre", 0,
                    "negatif", 0, "keywords", List.of());
        }

        long positif = analyses.stream()
                .filter(a -> "positif".equals(a.getSentiment())).count();
        long neutre  = analyses.stream()
                .filter(a -> "neutre".equals(a.getSentiment())).count();
        long negatif = analyses.stream()
                .filter(a -> "négatif".equals(a.getSentiment())).count();

        // Top keywords
        Map<String, Long> kwCount = analyses.stream()
                .filter(a -> a.getKeywords() != null && !a.getKeywords().isBlank())
                .flatMap(a -> Arrays.stream(a.getKeywords().split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        List<Map<String, Object>> topKeywords = kwCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(e -> Map.<String, Object>of(
                        "word", e.getKey(),
                        "count", e.getValue(),
                        "percent", Math.round((e.getValue() * 100.0) / total)
                ))
                .collect(Collectors.toList());

        return Map.of(
                "total",      total,
                "positif",    positif,
                "neutre",     neutre,
                "negatif",    negatif,
                "pctPositif", Math.round((positif * 100.0) / total),
                "pctNeutre",  Math.round((neutre  * 100.0) / total),
                "pctNegatif", Math.round((negatif * 100.0) / total),
                "keywords",   topKeywords
        );
    }
}