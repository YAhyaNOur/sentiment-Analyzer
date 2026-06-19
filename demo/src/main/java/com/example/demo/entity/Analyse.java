package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analyses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sentiment;

    private Double score;

    private Integer label;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avis_id")
    @JsonBackReference
    private Avis avis;

    public java.util.List<String> getKeywordsList() {
        if (keywords == null || keywords.isBlank()) return java.util.List.of();
        return java.util.List.of(keywords.split(","));
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}