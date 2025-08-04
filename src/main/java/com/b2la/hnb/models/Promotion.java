package com.b2la.hnb.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promotions") // Convention: nom de table en minuscules
public class Promotion { // Nom de classe en PascalCase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double discount;

    @Column(nullable = false)
    private Date debut;

    @Column(nullable = false)
    private Date fin;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false) // Correction: @Column pas nécessaire ici
    private Produit produit; // Type en PascalCase

    public Promotion() {
    }

    public Promotion(Long id, double discount, Date debut, Date fin, Produit produit) {
        this.id = id;
        this.discount = discount;
        this.debut = debut;
        this.fin = fin;
        this.produit = produit;
    }

    // Getters et setters (inchangés)
    // ...
}