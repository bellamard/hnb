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

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public categoryType getType() {
        return type;
    }

    public void setType(categoryType type) {
        this.type = type;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}