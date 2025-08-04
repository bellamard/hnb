package com.b2la.hnb.models;

import com.b2la.hnb.util.categoryType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits") // Convention: nom de table en minuscules
public class Produit { // Nom de classe en PascalCase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "prix_unitaire", nullable = false) // Meilleure pratique: préciser le nom de colonne
    private double prixUnitaire; // Convention Java: camelCase pour les attributs

    private String description;

    @Column(name = "quantite_stock", nullable = false)
    private int quantiteStock;

    @Enumerated(EnumType.STRING)
    private categoryType type; // À vérifier: categoryType devrait être CategoryType (PascalCase)

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true) // Changé "produit_id" en "produit"
    private List<Promotion> promotions = new ArrayList<>();

    public Produit() {
    }

    public Produit(Long id, String nom, double prixUnitaire, String description,
                   int quantiteStock, categoryType type) {
        this.id = id;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.description = description;
        this.quantiteStock = quantiteStock;
        this.type = type;
    }

    // Getters et setters (inchangés mais adaptés aux nouveaux noms)
    // ...
}