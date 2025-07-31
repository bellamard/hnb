package com.b2la.hnb.models;

import com.b2la.hnb.util.categoryType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="Produits")
public class produits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private double prix_unitaire;

    private String description;

    @Column(nullable = false)
    private int quantite_stock;

    @Enumerated(EnumType.STRING)
    private categoryType type;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<promotion> promotions= new ArrayList<>();

    public produits() {
    }

    public produits(Long id, String nom, double prix_unitaire, String description, int quantite_stock, categoryType type, List<promotion> promotions) {
        this.id = id;
        this.nom = nom;
        this.prix_unitaire = prix_unitaire;
        this.description = description;
        this.quantite_stock = quantite_stock;
        this.type = type;
        this.promotions = promotions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite_stock() {
        return quantite_stock;
    }

    public void setQuantite_stock(int quantite_stock) {
        this.quantite_stock = quantite_stock;
    }

    public categoryType getType() {
        return type;
    }

    public void setType(categoryType type) {
        this.type = type;
    }

    public List<promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<promotion> promotions) {
        this.promotions = promotions;
    }
}
