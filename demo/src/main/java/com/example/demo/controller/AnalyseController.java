package com.example.demo.controller;

import com.example.demo.entity.Analyse;
import com.example.demo.service.AnalyseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
public class AnalyseController {

    private final AnalyseService analyseService;

    @GetMapping("/produit/{id}")
    public ResponseEntity<List<Analyse>> getByProduit(@PathVariable Long id) {
        return ResponseEntity.ok(analyseService.getByProduit(id));
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        return ResponseEntity.ok(analyseService.getStats(id));
    }
}