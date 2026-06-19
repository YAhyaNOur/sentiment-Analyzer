package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texte;

    @Column(nullable = false)
    private Integer note;  // 1-5 étoiles

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private User client;

    @OneToOne(mappedBy = "avis", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Analyse analyse;


    public Long getProduitId() {
        return produit != null ? produit.getId() : null;
    }

    public String getProduitNom() {
        return produit != null ? produit.getNom() : null;
    }

    public String getClientEmail() {
        return client != null ? client.getEmail() : null;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}